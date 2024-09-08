package hangman.infrastructure.category

import hangman.domain.dto.{CategoryDto, WordDto}

trait CategoryRepository:
    def getRandomCategory: Option[CategoryDto]
    def getCategoryByName(categoryName: String): Option[CategoryDto]
    def getAllCategories: Option[List[CategoryDto]]
