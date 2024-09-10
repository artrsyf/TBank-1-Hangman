package hangman.application.game_state.default_game_state

import scala.util.Random

import hangman.infrastructure.category.CategoryRepository
import hangman.application.game_state.GameState
import hangman.shared.enums.Difficulty
import hangman.shared.constants.GameConfig

case class DefaultSessionGameState(
  private val categoryRepo: CategoryRepository,
  private val choosenDifficulty: Option[Difficulty] = None,
  private val choosenCategory: Option[String] = None,
  private val answer: Option[String] = None,
  private val wordHint: Option[String] = None,
  private val adt: Set[Char] = Set.empty,
  private val attemptsToAnswerCount: Option[Int] = None,
  private val curentAttemptsCount: Int = 0,
  private val isEnded: Boolean = false
) extends GameState {

  override def initializeParams(
    difficulty: Difficulty,
    categoryName: String
  ): GameState =
    val category =
      if categoryName == "Random" then categoryRepo.getRandomCategory
      else categoryRepo.getCategoryByName(categoryName)

    val (randomWord, hint, processedCategoryName) = category match
      case Some(category) =>
        val suitableWords =
          category.words.filter(_.difficulty == difficulty).toSeq
        val randomWordObject = suitableWords(
          Random.nextInt(suitableWords.size - 1)
        )
        (
          Some(randomWordObject.content),
          Some(randomWordObject.hint),
          Some(category.name)
        )

      case _ => (None, None, None)

    copy(
      choosenDifficulty = Some(difficulty),
      choosenCategory = processedCategoryName,
      answer = randomWord,
      wordHint = hint,
      attemptsToAnswerCount = Some(GameConfig.maxAttempts)
    )

  override def getAnswerHint: String =
    wordHint match
      case Some(hint) => hint
      case None       => throw new Exception("Hint hasn't been initialized")

  override def getCategoryRepo: CategoryRepository = categoryRepo

  override def getCategory: String =
    choosenCategory match
      case Some(category) => category
      case None           => throw new Exception("Category hasn't been initialized")

  override def getDifficulty: Difficulty =
    choosenDifficulty match
      case Some(difficulty) => difficulty
      case None             => throw new Exception("Difficulty hasn't been initialized")

  override def getAnswer: String =
    answer match
      case Some(answer) => answer
      case None         => throw new Exception("Answer hasn't been initialized")

  override def getAdt: Set[Char] = adt

  override def getAttemptsToAnswerCount: Int =
    attemptsToAnswerCount match
      case Some(count) => count
      case None        => throw new Exception("Attempts havn't been initialized")

  override def getCurentAttemptsCount: Int = curentAttemptsCount

  override def isGameEnded: Boolean = isEnded

  override def guess(userInput: Char): GameState =
    val userInputToLower = userInput.toLower
    answer match
      case Some(answerString) =>
        val updatedAttemptsCount =
          if (
              answerString.toLowerCase
                .contains(userInput.toLower) && !adt.contains(userInput.toLower)
            )
          then curentAttemptsCount
          else curentAttemptsCount + 1

        val newAdt = adt + userInputToLower
        val newGameStatus = attemptsToAnswerCount match
          case Some(value) =>
            value == curentAttemptsCount + 1 || answerString.toLowerCase.forall(
              newAdt.contains
            )
          case _ => false

        copy(
          adt = newAdt,
          curentAttemptsCount = updatedAttemptsCount,
          isEnded = newGameStatus
        )

      case _ => throw new Exception("hasn't been initialized")
}
