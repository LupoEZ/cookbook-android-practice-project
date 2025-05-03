package com.ericz.cookbook.data.repositories

import com.ericz.cookbook.data.dao.IngredientDao
import com.ericz.cookbook.data.entities.Ingredient

class IngredientRepository(private val ingredientDao: IngredientDao) {
    suspend fun getAllIngredients(): List<Ingredient> {
        return ingredientDao.getAllIngredients()
    }

    suspend fun addIngredient(ingredient: Ingredient): Long {
        return ingredientDao.insert(ingredient)
    }

    suspend fun insertIngredient(ingredient: Ingredient) {
        ingredientDao.insert(ingredient)
    }
}
