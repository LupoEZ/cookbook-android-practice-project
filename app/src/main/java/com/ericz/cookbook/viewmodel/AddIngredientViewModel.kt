package com.ericz.cookbook.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ericz.cookbook.data.dao.IngredientDao
import com.ericz.cookbook.data.entities.Ingredient
import com.ericz.cookbook.data.repositories.IngredientRepository
import kotlinx.coroutines.launch

class AddIngredientViewModel(
    private val ingredientRepository: IngredientRepository
) : ViewModel() {
    var name by mutableStateOf("")
    var unit by mutableStateOf("")
    var calories by mutableStateOf("")
    var fat by mutableStateOf("")
    var carbs by mutableStateOf("")
    var protein by mutableStateOf("")

    fun updateName(newName: String) {
        name = newName
    }

    fun updateUnit(newUnit: String) {
        unit = newUnit
    }

    fun updateCalories(newCalories: String) {
        calories = newCalories
    }

    fun updateFat(newFat: String) {
        fat = newFat
    }

    fun updateCarbs(newCarbs: String) {
        carbs = newCarbs
    }

    fun updateProtein(newProtein: String) {
        protein = newProtein
    }

    fun saveIngredient() {
        viewModelScope.launch {
            val ingredient = Ingredient(
                name = name,
                unit = unit,
                calories = calories.toFloatOrNull() ?: 0f,
                fat = fat.toFloatOrNull() ?: 0f,
                carbs = carbs.toFloatOrNull() ?: 0f,
                protein = protein.toFloatOrNull() ?: 0f
            )
            ingredientRepository.insertIngredient(ingredient) // Use the repository method
        }
    }
}



