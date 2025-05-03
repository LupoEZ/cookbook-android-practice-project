package com.ericz.cookbook.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ericz.cookbook.data.dao.RecipeDao
import com.ericz.cookbook.data.entities.Recipe
import com.ericz.cookbook.data.repositories.RecipeRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class RecipeViewModel(private val recipeRepository: RecipeRepository) : ViewModel() {

    // Flow of recipes from the repository
    val recipes: StateFlow<List<Recipe>> = recipeRepository.getAllRecipes()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
}


