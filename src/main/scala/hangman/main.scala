package hangman

import hangman.infrastructure.category.memory.CategoryMemoryRepository
import hangman.presentation.ConsoleDisplay
import hangman.application.session.default_session.DefaultSession
import hangman.application.controller.GameController
import hangman.application.game_state.default_game_state.DefaultSessionGameState

@main def hello() = {
  val repo = CategoryMemoryRepository()
  val gameState = DefaultSessionGameState(repo)
  val userSession = DefaultSession(1, gameState)
  val gameController = GameController(userSession)
  val gameConsole = ConsoleDisplay(gameController)

  // Инициализация параметров игры
  val initedGameConsole = gameConsole.readAndUpdateUserParams

  // Запуск игрового цикла
  gameLoop(initedGameConsole)
}

def gameLoop(consoleDisplay: ConsoleDisplay): Unit = {
  // Рендерим текущее состояние игры
  val updatedGameConsole = consoleDisplay.renderGame

  if !updatedGameConsole.gameController.userSession.getCurrentGameState.isGameEnded then
    gameLoop(updatedGameConsole)
  else updatedGameConsole.renderEnding
}

  