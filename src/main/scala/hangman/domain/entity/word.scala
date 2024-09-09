package hangman.domain.entity

import hangman.shared.enums.Difficulty

case class Word(
    val content: String,
    val hint: String,
    val difficulty: Difficulty
)
