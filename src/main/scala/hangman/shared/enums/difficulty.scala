package hangman.shared.enums

import scala.util.Random

enum Difficulty:
  case Easy, Medium, Hard

object Difficulty:
  def random: Difficulty =
    val values = Difficulty.values
    values(Random.nextInt(values.length))
