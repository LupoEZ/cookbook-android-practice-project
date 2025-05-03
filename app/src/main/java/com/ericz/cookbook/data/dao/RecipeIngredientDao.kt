package com.ericz.cookbook.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ericz.cookbook.data.entities.RecipeIngredient

@Dao
interface RecipeIngredientDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipeIngredients(recipeIngredient: List<RecipeIngredient>)

    @Query("""
        SELECT ri.amount, i.*
        FROM recipe_ingredients AS ri
        INNER JOIN ingredients AS i ON ri.ingredientId = i.id
        WHERE ri.recipeId = :recipeId
    """)
    suspend fun getRecipeIngredientsWithAmount(recipeId: Int): List<IngredientWithAmount>
}

data class IngredientWithAmount(
    val id: Int,
    val name: String,
    val unit: String,
    val calories: Float,
    val fat: Float,
    val carbs: Float,
    val protein: Float,
    val amount: Float
)

