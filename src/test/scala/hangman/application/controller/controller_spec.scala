package hangman.application.controller

import org.scalatest.funsuite.AnyFunSuite
import scala.util.Random

import hangman.infrastructure.category.memory.CategoryMemoryRepository
import hangman.application.session.default_session.DefaultSession
import hangman.application.game_state.default_game_state.DefaultSessionGameState
import hangman.shared.enums.Difficulty
import hangman.shared.constants.GameConfig

class GameControllerSpec extends AnyFunSuite:
  test("GameController.processUserInput success"):
    val repo = CategoryMemoryRepository()
    val gameState = DefaultSessionGameState(repo)
    val userSession = DefaultSession(GameConfig.exampleUserId, gameState)
    val gameController = GameController(userSession)

    val difficultyNumber = 2
    val categoryString = "Fruits"
    val initializedGameController =
      gameController.processUserInput(difficultyNumber, categoryString)

    val initedCategory =
      initializedGameController.getCurrentUserSession.getCurrentGameState.getCategory
    val initedDifficulty =
      initializedGameController.getCurrentUserSession.getCurrentGameState.getDifficulty

    assert(initedCategory == categoryString)
    assert(initedDifficulty == Difficulty.Medium)

  test("GameController.getGuessString success"):
    val repo = CategoryMemoryRepository()
    val gameState = DefaultSessionGameState(repo)
    val userSession = DefaultSession(GameConfig.exampleUserId, gameState)
    val gameController = GameController(userSession)

    val difficultyNumber = 2
    val categoryString = "Fruits"
    val initializedGameController =
      gameController.processUserInput(difficultyNumber, categoryString)

    val defaultGuessString = initializedGameController.getGuessString
    val answer =
      initializedGameController.getCurrentUserSession.getCurrentGameState.getAnswer

    assert(defaultGuessString == "_" * answer.size)

    val randomCharIndex = Random.nextInt(answer.size)
    val randomChar = answer(randomCharIndex)
    val processedGuess =
      answer.map(char => if char == randomChar then char else '_')

    val afterGuessGameController = initializedGameController.guess(randomChar)
    val filledGuessString = afterGuessGameController.getGuessString

    assert(filledGuessString == processedGuess)

  test("GameController.getOpenCategories success"):
    val repo = CategoryMemoryRepository()
    val gameState = DefaultSessionGameState(repo)
    val userSession = DefaultSession(GameConfig.exampleUserId, gameState)
    val gameController = GameController(userSession)

    val difficultyNumber = 2
    val categoryString = "Fruits"
    val initializedGameController =
      gameController.processUserInput(difficultyNumber, categoryString)

    val categoriesFromRepo = repo.getAllCategories match
      case Some(categories) => categories.map(category => category.name)
      case _                => List()

    assert(categoriesFromRepo.size != 0)
    initializedGameController.getOpenCategories.map(categoryString =>
      assert(categoriesFromRepo.contains(categoryString))
    )

  test("GameController.guess success"):
    val repo = CategoryMemoryRepository()
    val gameState = DefaultSessionGameState(repo)
    val userSession = DefaultSession(GameConfig.exampleUserId, gameState)
    val gameController = GameController(userSession)

    val difficultyNumber = 2
    val categoryString = "Fruits"
    val initializedGameController =
      gameController.processUserInput(difficultyNumber, categoryString)

    val answer =
      initializedGameController.getCurrentUserSession.getCurrentGameState.getAnswer
    val randomCharIndex = Random.nextInt(answer.size)
    val correctChar = answer(randomCharIndex)
    val afterGuessGameController = initializedGameController.guess(correctChar)

    assert(
      afterGuessGameController.getCurrentUserSession.getCurrentGameState.getAdt
        .contains(correctChar)
    )
    assert(
      afterGuessGameController.getCurrentUserSession.getCurrentGameState.getCurentAttemptsCount == 0
    )

    val incorrectChar = '1'
    val afterIncorrectGuessameController =
      afterGuessGameController.guess(incorrectChar)

    assert(
      afterIncorrectGuessameController.getCurrentUserSession.getCurrentGameState.getAdt
        .contains(incorrectChar)
    )
    assert(
      afterIncorrectGuessameController.getCurrentUserSession.getCurrentGameState.getCurentAttemptsCount == 1
    )
