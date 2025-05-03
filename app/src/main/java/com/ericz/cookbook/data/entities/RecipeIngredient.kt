package com.ericz.cookbook.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "recipe_ingredients",
    primaryKeys = ["recipeId", "ingredientId"],
    foreignKeys = [
        ForeignKey(entity = Recipe::class, parentColumns = ["id"], childColumns = ["recipeId"]),
        ForeignKey(entity = Ingredient::class, parentColumns = ["id"], childColumns = ["ingredientId"])
    ]
)
data class RecipeIngredient(
    val recipeId: Int,
    val ingredientId: Int,
    val amount: Float
)