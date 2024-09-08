package hangman.infrastructure.category.memory

import scala.util.Random

import hangman.domain.entity.{Category => WordCategory, Word}
import hangman.domain.dto.{CategoryDto, WordDto}
import hangman.infrastructure.category.CategoryRepository
import hangman.shared.enums.Difficulty

class CategoryMemoryRepository() extends CategoryRepository:
  val categories: Map[String, WordCategory] = Map(
    "Animals" -> WordCategory(
      "Animals",
      List(Word("Cat", "hint1", Difficulty.Easy), Word("Dog", "hint1", Difficulty.Easy))
    ),
    "Fruits" -> WordCategory(
      "Fruits",
      List(Word("Apple", "hint1", Difficulty.Medium), Word("Banana", "hint1", Difficulty.Easy))
    ),
    "Colors" -> WordCategory(
      "Colors",
      List(Word("Red", "hint1", Difficulty.Easy), Word("Blue", "hint1", Difficulty.Easy))
    )
  )

  override def getRandomCategory: Option[CategoryDto] =
    categories.values.toSeq match
      case Nil => None
      case seq =>
        val randomCategory = seq(Random.nextInt(seq.size))
        Some(
          CategoryDto(
            randomCategory.name,
            randomCategory.words.map(word => WordDto(word.content, word.hint, word.difficulty))
          )
        )

  override def getCategoryByName(categoryName: String): Option[CategoryDto] =
    categories
      .get(categoryName)
      .map(category =>
        CategoryDto(
          category.name,
          category.words.map(word => WordDto(word.content, word.hint, word.difficulty))
        )
      )

  override def getAllCategories: Option[List[CategoryDto]] =
    Option(
      categories.values
        .map(category =>
          CategoryDto(
            category.name,
            category.words.map(word => WordDto(word.content, word.hint, word.difficulty))
          )
        )
        .toList
    ).filter(_.nonEmpty)
