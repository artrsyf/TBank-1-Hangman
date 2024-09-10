package hangman.application.session.default_session

import org.scalatest.funsuite.AnyFunSuite

import hangman.infrastructure.category.memory.CategoryMemoryRepository
import hangman.application.game_state.default_game_state.DefaultSessionGameState
import hangman.shared.constants.GameConfig

class DefaultSessionSpec extends AnyFunSuite:
    test("DefaultSessionSpec.defineGameParams success"):
      val repo = CategoryMemoryRepository()
      val gameState = DefaultSessionGameState(repo)
      val userSession = DefaultSession(GameConfig.exampleUserId, gameState)
      
      val difficultyNumber = 2
      val categoryString = "Fruits"
      val initializedUserSession = userSession.defineGameParams(difficultyNumber, categoryString)

      assert(initializedUserSession.getCurrentGameState.getCategory == categoryString)

    test("DefaultSessionSpec.guess success"):
      val repo = CategoryMemoryRepository()
      val gameState = DefaultSessionGameState(repo)
      val userSession = DefaultSession(GameConfig.exampleUserId, gameState)
      
      val difficultyNumber = 2
      val categoryString = "Fruits"
      val initializedUserSession = userSession.defineGameParams(difficultyNumber, categoryString)
      
      val initializedGameState = initializedUserSession.getCurrentGameState
      val answer = initializedGameState.getAnswer
      val correctChar = answer(0).toLower
      val afterGuessSession = initializedUserSession.guess(correctChar)

      assert(afterGuessSession.getCurrentGameState.getAdt.contains(correctChar))
      assert(afterGuessSession.getCurrentGameState.getCurentAttemptsCount == 0)

      val incorrectChar = '1'
      val afterIncorrectGuessSession = afterGuessSession.guess(incorrectChar)
      
      assert(afterIncorrectGuessSession.getCurrentGameState.getAdt.contains(incorrectChar))
      assert(afterIncorrectGuessSession.getCurrentGameState.getCurentAttemptsCount == 1)
