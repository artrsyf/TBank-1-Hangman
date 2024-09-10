package hangman.presentation

import scala.io.StdIn
import scala.util.Try

import hangman.application.controller.GameController
import hangman.application.session.Session
import hangman.shared.enums.Difficulty
import hangman.application.game_state.GameState
import hangman.shared.constants.ConsoleOutput

case class ConsoleDisplay(
  private val gameController: GameController
):

  def getGameController: GameController = gameController

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

    println("\nChoose a category:")

    val categories = gameController.getOpenCategories.zipWithIndex
    categories.foreach { case (category, index) =>
      println(s"${index + 1}) $category")
    }

    val categoryInput = StdIn.readLine()
    val categoryNumber = Try(categoryInput.toInt).toOption match {
      case Some(number) if number > 0 && number <= categories.size => number
      case _                                                       => -1
    }

    val category =
      if categoryNumber > 0 then
        gameController.getOpenCategories(categoryNumber - 1)
      else "Random"

    val initedGameController =
      gameController.processUserInput(difficultyNumber, category)

    copy(gameController = initedGameController)

  def clearConsole: Unit =
    val os = System.getProperty("os.name").toLowerCase
    
    if os.contains("win") then
      new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor()
    else Runtime.getRuntime.exec("clear")

  def renderEnding: Unit =
    clearConsole

    val currentAttemptsCount =
      gameController.getCurrentUserSession.getCurrentGameState.getCurentAttemptsCount
    val attemptsToAnswerCount =
      gameController.getCurrentUserSession.getCurrentGameState.getAttemptsToAnswerCount

    if currentAttemptsCount == attemptsToAnswerCount then renderLoseEnding
    else renderWinEnding

  def renderLoseEnding: Unit =
    println("You've lost")
    println(ConsoleOutput.loseScreenHangman)

  def renderWinEnding: Unit =
    println("You've won")

  def renderGame: ConsoleDisplay =
    clearConsole

    val gameCategory =
      gameController.getCurrentUserSession.getCurrentGameState.getCategory
    val gameDifficulty =
      gameController.getCurrentUserSession.getCurrentGameState.getDifficulty
    val currentAttemptsCount =
      gameController.getCurrentUserSession.getCurrentGameState.getCurentAttemptsCount
    val attemptsToAnswerCount =
      gameController.getCurrentUserSession.getCurrentGameState.getAttemptsToAnswerCount
    val gameStatus = gameController.getCurrentUserSession.getCurrentGameState.isGameEnded
    val answerHint =
      gameController.getCurrentUserSession.getCurrentGameState.getAnswerHint

    if gameStatus then
      renderEnding
      ConsoleDisplay(gameController)

    println(s"Category: ${gameCategory}")
    println(s"Difficulty: ${gameDifficulty}")
    println("\n")

    println(
      "Attempts left: " +
        s" ${attemptsToAnswerCount - currentAttemptsCount}"
    )
    println("\n")

    currentAttemptsCount match
      case 0 => println(ConsoleOutput.sixAttemtsLeftHangman)
      case 1 => println(ConsoleOutput.fiveAttemtsLeftHangman)
      case 2 => println(ConsoleOutput.fourAttemtsLeftHangman)
      case 3 =>
        println(s"Hint: $answerHint")
        println(ConsoleOutput.threeAttemtsLeftHangman)
      case 4 => println(ConsoleOutput.twoAttemtsLeftHangman)
      case 5 => println(ConsoleOutput.oneAttemtsLeftHangman)
      case _ => println("No attempts left")
    println("\n")

    println(gameController.getGuessString)
    println("\n")
    
    println("Enter some char:")
    val inputChar = StdIn.readChar()

    guess(inputChar)
