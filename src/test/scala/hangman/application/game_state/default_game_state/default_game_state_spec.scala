package hangman.application.game_state.default_game_state

import org.scalatest.funsuite.AnyFunSuite

import hangman.infrastructure.category.memory.CategoryMemoryRepository
import hangman.shared.enums.Difficulty
import hangman.shared.constants.GameConfig

class DefaultSessionGameStateSpec extends AnyFunSuite:
  test("DefaultSessionGameState.initializeParams success"):
    val categoryRepo = CategoryMemoryRepository()
    val gameState = DefaultSessionGameState(categoryRepo)

    val difficulty = Difficulty.Medium
    val categoryString = "Fruits"
    val initializedGame = gameState.initializeParams(difficulty, categoryString)

    assert(initializedGame.getCategory == "Fruits")
    assert(initializedGame.getDifficulty == Difficulty.Medium)
    assert(initializedGame.getAttemptsToAnswerCount == GameConfig.maxAttempts)
    assert(initializedGame.isGameEnded == false)

    val categoryDto = categoryRepo.getCategoryByName(categoryString)
    val categoryWordDtos = categoryDto match
      case Some(category) => category.words
      case _ => List()

    val words = categoryWordDtos.map(wordDto => wordDto.content)
    val currentWordDto = categoryWordDtos.find(wordDto => wordDto.content == initializedGame.getAnswer)

    assert(currentWordDto != None)
    currentWordDto match
      case Some(wordDto) => 
        assert(wordDto.content == initializedGame.getAnswer)
        assert(wordDto.hint == initializedGame.getAnswerHint)

  test("DefaultSessionGameState.guess success"):
    val categoryRepo = CategoryMemoryRepository()
    val gameState = DefaultSessionGameState(categoryRepo)

    val difficulty = Difficulty.Medium
    val categoryString = "Fruits"
    val initializedGame = gameState.initializeParams(difficulty, categoryString)

    val answer = initializedGame.getAnswer
    val correctChar = answer(0).toLower
    val afterGuessGame = initializedGame.guess(correctChar)

    assert(afterGuessGame.getAdt.contains(correctChar))
    assert(afterGuessGame.getCurentAttemptsCount == 0)

    val incorrectChar = '1'
    val afterIncorrectGuessGame = afterGuessGame.guess(incorrectChar)
    assert(afterIncorrectGuessGame.getAdt.contains(incorrectChar))
    assert(afterIncorrectGuessGame.getCurentAttemptsCount == 1)

