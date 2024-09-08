package hangman.application.game_state

import hangman.application.game_difficulty.Difficulty
import hangman.infrastructure.category.CategoryRepository

trait GameState:
    def getCategoryRepo: CategoryRepository
    def initializeParams(difficulty: Difficulty, category: String): GameState
    def getCategory: String
    def getDifficulty: Difficulty
    def getAnswer: String
    def getAdt: Set[Char]
    def getAttemptsToAnswerCount: Int
    def getCurentAttemptsCount: Int
    def isGameEnded: Boolean

    def guess(userInput: Char): GameState