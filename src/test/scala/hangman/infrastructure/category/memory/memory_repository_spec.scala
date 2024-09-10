package hangman.infrastructure.category.memory

import org.scalatest.funsuite.AnyFunSuite

class CategoryMemoryRepositorySpec extends AnyFunSuite: // pending
  test("CategoryMemoryRepository.getRandomCategory success"):
    val repo = CategoryMemoryRepository()

    assert(repo.getRandomCategory != None)
  
  test("CategoryMemoryRepository.getCategoryByName success"):
    val repo = CategoryMemoryRepository()
    val testCategoryName = "Fruits"

    assert(repo.getCategoryByName(testCategoryName) != None)
  
  test("CategoryMemoryRepository.getAllCategories success"):
    val repo = CategoryMemoryRepository()
    val categories = repo.getAllCategories

    assert(categories != None && categories.size != 0)
