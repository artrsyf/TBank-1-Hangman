package hangman.domain.dto

import hangman.shared.enums.Difficulty

case class WordDto(
    val content: String,
    val hint: String,
    val difficulty: Difficulty
)
