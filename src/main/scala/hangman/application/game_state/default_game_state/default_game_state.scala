package hangman.application.game_state.default_game_state

import scala.util.Random

import hangman.infrastructure.category.CategoryRepository
import hangman.application.game_state.GameState
import hangman.application.game_difficulty.Difficulty

case class DefaultSessionGameState(
  val categoryRepo: CategoryRepository,
  val choosenDifficulty: Option[Difficulty] = None,
  val choosenCategory: Option[String] = None,
  val answer: Option[String] = None,
  val adt: Set[Char] = Set.empty, // Текущий результат
  val attemptsToAnswerCount: Option[Int] = None,
  val curentAttemptsCount: Int = 0,
  val isEnded: Boolean = false
) extends GameState {

  override def initializeParams(
    difficulty: Difficulty,
    categoryName: String
  ): GameState =
    val category = categoryRepo.getCategoryByName(categoryName)
    val randomWord = category match
      case Some(category) =>
        Some(category.words.toSeq(Random.nextInt(category.words.size)).content)
      case _ => None // Выод??
    copy(
      choosenDifficulty = Some(difficulty),
      choosenCategory = Some(categoryName),
      answer = randomWord,
      attemptsToAnswerCount = Some(6)
    )

  override def getCategoryRepo: CategoryRepository =
    categoryRepo

  override def getCategory: String =
    choosenCategory match
      case Some(category) => category
      case None           => throw new Exception("pending")

  override def getDifficulty: Difficulty =
    choosenDifficulty match
      case Some(difficulty) => difficulty
      case None             => throw new Exception("pending")

  override def getAnswer: String =
    answer match
      case Some(answer) => answer
      case None         => throw new Exception("pending")

  override def getAdt: Set[Char] =
    adt

  override def getAttemptsToAnswerCount: Int =
    attemptsToAnswerCount match
      case Some(count) => count
      case None        => throw new Exception("pending")

  override def getCurentAttemptsCount: Int =
    curentAttemptsCount

  override def isGameEnded: Boolean = 
    isEnded

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
            value == curentAttemptsCount + 1 || answerString.toLowerCase.forall(newAdt.contains)
          case _ => false

        copy(
          adt = newAdt,
          curentAttemptsCount = updatedAttemptsCount,
          isEnded = newGameStatus
        )

      case _ => throw new Exception("Answer is not generated")
}
