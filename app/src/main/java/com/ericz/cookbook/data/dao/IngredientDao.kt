package com.ericz.cookbook.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ericz.cookbook.data.entities.Ingredient

@Dao
interface IngredientDao {
    @Query("""
        SELECT i.* 
        FROM ingredients i
        INNER JOIN recipe_ingredients ri ON i.id = ri.ingredientId
        WHERE ri.recipeId = :recipeId
    """)
    suspend fun getIngredientsForRecipe(recipeId: Int): List<Ingredient>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(ingredient: Ingredient): Long

    @Query("SELECT * FROM ingredients")
    suspend fun getAllIngredients(): List<Ingredient> // This fetches all ingredients
}

