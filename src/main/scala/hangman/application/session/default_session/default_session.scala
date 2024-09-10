package hangman.application.session.default_session

import hangman.application.session.Session
import hangman.application.game_state.GameState
import hangman.shared.enums.Difficulty

case class DefaultSession(
  private val userId: Int,
  private val currentGameState: GameState
) extends Session {

  override def defineGameParams(
    difficultyNumber: Int,
    category: String
  ): Session =
    val difficulty = difficultyNumber match
      case 1 => Difficulty.Easy
      case 2 => Difficulty.Medium
      case 3 => Difficulty.Hard
      case _ => Difficulty.random

    val updatedGameState =
      currentGameState.initializeParams(difficulty, category)

    copy(currentGameState = updatedGameState)

  override def getCurrentGameState: GameState = currentGameState

  override def guess(userInput: Char): Session =
    val updatedGameState = currentGameState.guess(userInput)

    copy(currentGameState = updatedGameState)
}
