package hangman.application.controller

import hangman.application.session.Session

case class GameController(val userSession: Session):
  def processUserInput(
    difficultyNumber: Int,
    choosenCategory: String
  ): GameController =
    val initializedSession =
      userSession.defineGameParams(difficultyNumber, choosenCategory)

    copy(userSession = initializedSession)

  def getGuessString: String =
    val currentState = userSession.getCurrentGameState
    val adt = currentState.getAdt
    val answer = currentState.getAnswer

    answer.map {
      case ch if adt.contains(ch.toLower) => ch
      case _                              => '_'
    }.mkString

  def guess(userInput: Char): GameController =
    val updatedGameSession = userSession.guess(userInput)

    copy(userSession = updatedGameSession)

  def getOpenCategories: List[String] =
    val gameState = userSession.getCurrentGameState

    val categories = gameState.getCategoryRepo.getAllCategories.getOrElse(
      throw new NoSuchElementException("There is not a single category specified")
    )

    categories.map(category => category.name)
