package hangman.presentation

import hangman.application.controller.GameController
import hangman.application.session.Session
import hangman.shared.enums.Difficulty
import hangman.application.game_state.GameState
import hangman.application.session.default_session.DefaultSession
import hangman.application.game_state.default_game_state.DefaultSessionGameState
import hangman.infrastructure.category.memory.CategoryMemoryRepository
import hangman.shared.constants.ConsoleOutput

import scala.io.StdIn
import scala.util.Try

case class ConsoleDisplay(
    val gameController: GameController
) {

  def guess(userInput: Char): ConsoleDisplay = 
        val updatedGameController = gameController.guess(userInput)

        copy(gameController = updatedGameController)

  def readAndUpdateUserParams: ConsoleDisplay =
    println(ConsoleOutput.difficultyDialog)

    val difficultyInput = StdIn.readLine()
    val difficultyNumber = Try(difficultyInput.toInt).toOption match {
      case Some(1) | Some(2) | Some(3) => difficultyInput.toInt
      case _                           => -1
    }

    println("Choose a category:")

    val categories = gameController.getOpenCategories.zipWithIndex
    categories.foreach {
      case (category, index) =>
        println(s"${index + 1}) $category")
    }

    val categoryInput = StdIn.readLine()
    val categoryNumber = Try(categoryInput.toInt).toOption match {
      case Some(number) if number > 0 && number <= categories.size => number
      case _                           => -1
    }
    
    val category = 
      if categoryNumber > 0 then
        gameController.getOpenCategories(categoryNumber - 1)
      else
        "Random"

    val initedGameController =
      gameController.processUserInput(difficultyNumber, category)

    copy(gameController = initedGameController)

  def clearConsole: Unit =
    val os = System.getProperty("os.name").toLowerCase
    if os.contains("win") then
      new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor()
    else
      Runtime.getRuntime.exec("clear")

  def renderEnding: Unit = 
    clearConsole
    
    val currentAttemptsCount = gameController.userSession.getCurrentGameState.getCurentAttemptsCount
    val attemptsToAnswerCount = gameController.userSession.getCurrentGameState.getAttemptsToAnswerCount

    if currentAttemptsCount == attemptsToAnswerCount then renderLoseEnding
    else renderWinEnding

  def renderLoseEnding: Unit = 
    println("Lose")
    println(ConsoleOutput.loseScreenHangman)

  def renderWinEnding: Unit = 
    println("Win")

  def renderGame: ConsoleDisplay =
    clearConsole

    val gameCategory = gameController.userSession.getCurrentGameState.getCategory
    val gameDifficulty = gameController.userSession.getCurrentGameState.getDifficulty
    val currentAttemptsCount = gameController.userSession.getCurrentGameState.getCurentAttemptsCount
    val attemptsToAnswerCount = gameController.userSession.getCurrentGameState.getAttemptsToAnswerCount
    val gameStatus = gameController.userSession.getCurrentGameState.isGameEnded
    val answerHint = gameController.userSession.getCurrentGameState.getAnswerHint

    if gameStatus then 
      renderEnding
      ConsoleDisplay(gameController)

    println(s"Category: ${gameCategory}")
    println(s"Difficulty: ${gameDifficulty}")
    println("|")
    println("|")
    println("|")
    println(
      "Attempts left: " +
        s" ${attemptsToAnswerCount - currentAttemptsCount}"
    )
    println("|")
    println("|")
    println("|")
    
    currentAttemptsCount match
      case 0 => println(ConsoleOutput.sixAttemtsLeftHangman)
      case 1 => println(ConsoleOutput.fiveAttemtsLeftHangman)
      case 2 => println(ConsoleOutput.fourAttemtsLeftHangman)
      case 3 => 
        println(s"Hint: $answerHint")
        println(ConsoleOutput.threeAttemtsLeftHangman)
      case 4 => println(ConsoleOutput.twoAttemtsLeftHangman)
      case 5 => println(ConsoleOutput.oneAttemtsLeftHangman)
      case _ => println("pending")

    println("|")
    println("|")
    println("|")
    println(gameController.getGuessString)
    println("|")
    println("|")
    println("|")
    println("Enter some char:")
    val inputChar = StdIn.readChar()

    guess(inputChar)
}
