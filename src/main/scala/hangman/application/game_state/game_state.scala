package hangman.application.game_state

import hangman.shared.enums.Difficulty
import hangman.infrastructure.category.CategoryRepository

trait GameState:
  def initializeParams(difficulty: Difficulty, category: String): GameState
  def getCategoryRepo: CategoryRepository
  def getCategory: String
  def getDifficulty: Difficulty
  def getAnswer: String
  def getAdt: Set[Char]
  def getAttemptsToAnswerCount: Int
  def getCurentAttemptsCount: Int
  def getAnswerHint: String
  def isGameEnded: Boolean

  def guess(userInput: Char): GameState
