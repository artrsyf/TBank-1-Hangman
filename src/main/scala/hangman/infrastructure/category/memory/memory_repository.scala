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
      List(
        Word("Cat", "A small domesticated carnivorous mammal", Difficulty.Easy),
        Word(
          "Dog",
          "A domesticated carnivorous mammal that typically has a long snout",
          Difficulty.Easy
        ),
        Word("Elephant", "A large mammal with a trunk", Difficulty.Hard),
        Word(
          "Giraffe",
          "The tallest living terrestrial animal",
          Difficulty.Hard
        ),
        Word("Lion", "Known as the king of the jungle", Difficulty.Medium),
        Word(
          "Kangaroo",
          "An animal known for its jumping ability",
          Difficulty.Medium
        )
      )
    ),
    "Fruits" -> WordCategory(
      "Fruits",
      List(
        Word(
          "Apple",
          "A round fruit with red or green skin",
          Difficulty.Medium
        ),
        Word("Banana", "A long curved fruit with yellow skin", Difficulty.Easy),
        Word(
          "Pineapple",
          "A tropical fruit with spiky skin and sweet flesh",
          Difficulty.Hard
        ),
        Word(
          "Strawberry",
          "A red fruit with seeds on the outside",
          Difficulty.Hard
        ),
        Word(
          "Grapes",
          "Small round fruits often used to make wine",
          Difficulty.Medium
        ),
        Word(
          "Watermelon",
          "A large fruit with green skin and red flesh",
          Difficulty.Hard
        )
      )
    ),
    "Colors" -> WordCategory(
      "Colors",
      List(
        Word("Red", "The color of fire and blood", Difficulty.Easy),
        Word("Blue", "The color of the sky and the ocean", Difficulty.Easy),
        Word(
          "Turquoise",
          "A blue-green color named after a mineral",
          Difficulty.Hard
        ),
        Word("Magenta", "A purplish-red color", Difficulty.Medium),
        Word("Crimson", "A rich, deep red color", Difficulty.Medium),
        Word("Chartreuse", "A color between yellow and green", Difficulty.Hard)
      )
    ),
    "Countries" -> WordCategory(
      "Countries",
      List(
        Word("Japan", "An island country in East Asia", Difficulty.Medium),
        Word(
          "Brazil",
          "The largest country in South America",
          Difficulty.Medium
        ),
        Word(
          "Germany",
          "A country known for its beer and cars",
          Difficulty.Medium
        ),
        Word(
          "Switzerland",
          "Known for its mountains and chocolate",
          Difficulty.Hard
        ),
        Word(
          "Australia",
          "The smallest continent and a country",
          Difficulty.Hard
        ),
        Word("Egypt", "Home to the Great Pyramids", Difficulty.Medium)
      )
    ),
    "Sports" -> WordCategory(
      "Sports",
      List(
        Word("Soccer", "The world's most popular sport", Difficulty.Medium),
        Word(
          "Basketball",
          "A sport involving shooting hoops",
          Difficulty.Medium
        ),
        Word(
          "Tennis",
          "Played with rackets and a small ball",
          Difficulty.Medium
        ),
        Word(
          "Archery",
          "A sport of shooting arrows with a bow",
          Difficulty.Hard
        ),
        Word(
          "Gymnastics",
          "A sport involving acrobatic exercises",
          Difficulty.Hard
        ),
        Word(
          "Baseball",
          "A bat-and-ball game played between two teams",
          Difficulty.Hard
        )
      )
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
            randomCategory.words.map(word =>
              WordDto(word.content, word.hint, word.difficulty)
            )
          )
        )

  override def getCategoryByName(categoryName: String): Option[CategoryDto] =
    categories
      .get(categoryName)
      .map(category =>
        CategoryDto(
          category.name,
          category.words
            .map(word => WordDto(word.content, word.hint, word.difficulty))
        )
      )

  override def getAllCategories: Option[List[CategoryDto]] =
    Option(
      categories.values
        .map(category =>
          CategoryDto(
            category.name,
            category.words.map(word =>
              WordDto(word.content, word.hint, word.difficulty)
            )
          )
        )
        .toList
    ).filter(_.nonEmpty)
