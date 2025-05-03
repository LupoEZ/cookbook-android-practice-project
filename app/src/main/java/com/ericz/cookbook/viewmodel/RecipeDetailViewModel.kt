package com.ericz.cookbook.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ericz.cookbook.data.dao.IngredientDao
import com.ericz.cookbook.data.dao.IngredientWithAmount
import com.ericz.cookbook.data.dao.InstructionDao
import com.ericz.cookbook.data.dao.RecipeDao
import com.ericz.cookbook.data.dao.RecipeIngredientDao
import com.ericz.cookbook.data.entities.Instruction
import com.ericz.cookbook.data.entities.Recipe
import com.ericz.cookbook.data.repositories.InstructionRepository
import com.ericz.cookbook.data.repositories.RecipeIngredientRepository
import com.ericz.cookbook.data.repositories.RecipeRepository
import kotlinx.coroutines.launch

class RecipeDetailViewModel(
    private val recipeRepository: RecipeRepository,
    private val recipeIngredientRepository: RecipeIngredientRepository,
    private val instructionRepository: InstructionRepository
) : ViewModel() {

    private val _recipeDetails = MutableLiveData<RecipeDetails?>()
    val recipeDetails: MutableLiveData<RecipeDetails?> get() = _recipeDetails

    fun loadRecipeDetails(recipeId: Int) {
        viewModelScope.launch {
            try {
                // Use the repositories to get data
                val recipe = recipeRepository.getRecipeById(recipeId)
                val ingredients = recipeIngredientRepository.getRecipeIngredientsWithAmount(recipeId)
                val instructions = instructionRepository.getInstructionsForRecipe(recipeId)

                _recipeDetails.postValue(
                    RecipeDetails(recipe, ingredients, instructions)
                )
            } catch (e: Exception) {
                _recipeDetails.postValue(null)
            }
        }
    }
}


data class RecipeDetails(
    val recipe: Recipe,
    val ingredients: List<IngredientWithAmount>,
    val instructions: List<Instruction>
)
