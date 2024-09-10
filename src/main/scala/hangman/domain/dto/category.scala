package hangman.domain.dto

case class CategoryDto(
  val name: String,
  val words: List[WordDto]
)
