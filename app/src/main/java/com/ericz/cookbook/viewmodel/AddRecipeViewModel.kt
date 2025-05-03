package com.ericz.cookbook.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ericz.cookbook.data.dao.IngredientDao
import com.ericz.cookbook.data.dao.InstructionDao
import com.ericz.cookbook.data.dao.RecipeDao
import com.ericz.cookbook.data.dao.RecipeIngredientDao
import com.ericz.cookbook.data.entities.Ingredient
import com.ericz.cookbook.data.entities.Instruction
import com.ericz.cookbook.data.entities.Recipe
import com.ericz.cookbook.data.entities.RecipeIngredient
import com.ericz.cookbook.data.repositories.IngredientRepository
import com.ericz.cookbook.data.repositories.InstructionRepository
import com.ericz.cookbook.data.repositories.RecipeIngredientRepository
import com.ericz.cookbook.data.repositories.RecipeRepository
import kotlinx.coroutines.launch

class AddRecipeViewModel(
    private val recipeRepository: RecipeRepository,
    private val ingredientRepository: IngredientRepository,
    private val instructionRepository: InstructionRepository,
    private val recipeIngredientRepository: RecipeIngredientRepository
) : ViewModel() {

    var title by mutableStateOf("")
    var description by mutableStateOf("")
    val ingredients = mutableStateListOf<Ingredient>() // All ingredients from the repository
    val filteredIngredients = mutableStateListOf<Ingredient>() // Filtered search results
    val selectedIngredients = mutableStateListOf<SelectedIngredient>()
    val instructions = mutableStateListOf<Instruction>()

    private var instructionNumber = 1

    fun updateTitle(newTitle: String) {
        title = newTitle
    }

    fun updateDescription(newDescription: String) {
        description = newDescription
    }

    fun addInstruction() {
        instructions.add(Instruction(step = "", stepNumber = instructionNumber, recipeId = 0))
        instructionNumber++
    }

    fun removeInstruction(index: Int) {
        if (index in instructions.indices) {
            instructions.removeAt(index)
            updateStepNumbers()
        }
    }

    fun updateInstruction(index: Int, step: String) {
        if (index in instructions.indices) {
            instructions[index] = instructions[index].copy(step = step)
        }
    }

    private fun updateStepNumbers() {
        instructions.forEachIndexed { index, instruction ->
            instructions[index] = instruction.copy(stepNumber = index + 1)
        }
        instructionNumber = instructions.size + 1 // Reset counter
    }

    init {
        viewModelScope.launch {
            ingredients.addAll(ingredientRepository.getAllIngredients())
        }
    }

    fun filterIngredients(query: String) {
        filteredIngredients.clear()
        filteredIngredients.addAll(
            ingredients.filter { ingredient ->
                ingredient.name.contains(query, ignoreCase = true) &&
                        selectedIngredients.none { it.ingredient.id == ingredient.id }
            }
        )
    }

    fun selectIngredient(ingredient: Ingredient) {
        if (selectedIngredients.none { it.ingredient.id == ingredient.id }) {
            selectedIngredients.add(SelectedIngredient(ingredient))
            filteredIngredients.remove(ingredient)
        }
    }

    fun updateSelectedIngredientAmount(ingredientId: Int, newAmount: Float) {
        val index = selectedIngredients.indexOfFirst { it.ingredient.id == ingredientId }
        if (index != -1) {
            val updatedIngredient = selectedIngredients[index].copy(amount = newAmount)
            selectedIngredients[index] = updatedIngredient
        }
    }

    fun deselectIngredient(ingredient: Ingredient) {
        val selectedIngredient = selectedIngredients.find { it.ingredient.id == ingredient.id }
        if (selectedIngredient != null) {
            selectedIngredients.remove(selectedIngredient)
            filteredIngredients.add(ingredient) // Re-add to filtered results for future selection
        }
    }

    fun addIngredient(newIngredient: Ingredient) {
        viewModelScope.launch {
            val ingredientId = ingredientRepository.addIngredient(newIngredient).toInt()
            val updatedIngredient = newIngredient.copy(id = ingredientId)
            ingredients.add(updatedIngredient)
            filterIngredients("") // Reset filtered list
        }
    }

    fun saveRecipe() {
        viewModelScope.launch {
            // Save Recipe
            val recipeId = recipeRepository.addRecipe(
                Recipe(title = title, description = description)
            ).toInt()

            // Save Instructions
            val instructionsWithRecipeId = instructions.map {
                it.copy(recipeId = recipeId)
            }
            instructionRepository.addInstructions(instructionsWithRecipeId)

            // Save Recipe-Ingredient Links
            val recipeIngredients = selectedIngredients.map {
                RecipeIngredient(
                    recipeId = recipeId,
                    ingredientId = it.ingredient.id,
                    amount = it.amount
                )
            }
            recipeIngredientRepository.addRecipeIngredients(recipeIngredients)
        }
    }
}

data class SelectedIngredient(
    val ingredient: Ingredient,
    var amount: Float = 1.0F
)
