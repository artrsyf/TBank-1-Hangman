package hangman.application.session

import hangman.application.game_state.GameState

trait Session:
  def defineGameParams(difficultyNumber: Int, categroy: String): Session
  def getCurrentGameState: GameState
  
  def guess(userInput: Char): Session
