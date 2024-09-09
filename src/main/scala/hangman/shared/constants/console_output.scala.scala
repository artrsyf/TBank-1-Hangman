package hangman.shared.constants

object ConsoleOutput:
  val difficultyDialog: String = """Hangman game
            Choose your difficulty:
                1) Easy;
                2) Medium;
                3) Hard """

  val sixAttemtsLeftHangman: String = """
      +---+
      |   |
          |
          |
          |
          |
      =========
      """

  val fiveAttemtsLeftHangman: String = """
      +---+
      |   |
      O   |
          |
          |
          |
      =========
      """
  val fourAttemtsLeftHangman: String = """
      +---+
      |   |
      O   |
      |   |
          |
          |
      =========
      """
  val threeAttemtsLeftHangman: String = """
      +---+
      |   |
      O   |
     /|   |
          |
          |
      =========
      """
  val twoAttemtsLeftHangman: String = """
      +---+
      |   |
      O   |
     /|\  |
          |
          |
      =========
      """
  val oneAttemtsLeftHangman: String = """
      +---+
      |   |
      O   |
     /|\  |
     /    |
          |
      =========
      """

  val loseScreenHangman: String = """
      +---+
      |   |
      O   |
     /|\  |
     / \  |
          |
      =========
      """
