package com.ericz.cookbook.data.repositories

import com.ericz.cookbook.data.dao.RecipeDao
import com.ericz.cookbook.data.entities.Recipe
import kotlinx.coroutines.flow.Flow

class RecipeRepository(private val recipeDao: RecipeDao) {
    suspend fun addRecipe(recipe: Recipe): Long {
        return recipeDao.insert(recipe)
    }

    suspend fun getRecipeById(recipeId: Int): Recipe {
        return recipeDao.getRecipeById(recipeId)
    }

    fun getAllRecipes(): Flow<List<Recipe>> {
        return recipeDao.getAllRecipes()
    }
}
