package com.ericz.cookbook.data.repositories

import com.ericz.cookbook.data.dao.IngredientWithAmount
import com.ericz.cookbook.data.dao.RecipeIngredientDao
import com.ericz.cookbook.data.entities.RecipeIngredient

class RecipeIngredientRepository(private val recipeIngredientDao: RecipeIngredientDao) {
    suspend fun addRecipeIngredients(recipeIngredients: List<RecipeIngredient>) {
        recipeIngredientDao.insertRecipeIngredients(recipeIngredients)
    }

    suspend fun getRecipeIngredientsWithAmount(recipeId: Int): List<IngredientWithAmount> {
        return recipeIngredientDao.getRecipeIngredientsWithAmount(recipeId)
    }
}
