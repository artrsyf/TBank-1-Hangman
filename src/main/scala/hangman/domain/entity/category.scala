package hangman.domain.entity

case class Category(
    val name: String,
    val words: List[Word]
)
