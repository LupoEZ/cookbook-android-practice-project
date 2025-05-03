package com.ericz.cookbook.viewmodel

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ericz.cookbook.data.database.CookbookDatabase
import com.ericz.cookbook.data.repositories.IngredientRepository
import com.ericz.cookbook.data.repositories.InstructionRepository
import com.ericz.cookbook.data.repositories.RecipeIngredientRepository
import com.ericz.cookbook.data.repositories.RecipeRepository

@Composable
fun provideRecipeDetailViewModel(context: Context): RecipeDetailViewModel {
    val recipeDao = CookbookDatabase.getInstance(context).recipeDao()
    val ingredientDao = CookbookDatabase.getInstance(context).ingredientDao()
    val recipeIngredientDao = CookbookDatabase.getInstance(context).recipeIngredientDao()
    val instructionDao = CookbookDatabase.getInstance(context).instructionDao()

    val recipeRepository = RecipeRepository(recipeDao)
    val ingredientRepository = IngredientRepository(ingredientDao)
    val instructionRepository = InstructionRepository(instructionDao)
    val recipeIngredientRepository = RecipeIngredientRepository(recipeIngredientDao)

    return viewModel(
        factory = GenericViewModelFactory(
            RecipeDetailViewModel::class.java
        ) {
            RecipeDetailViewModel(
                recipeRepository,
                recipeIngredientRepository,
                instructionRepository
            )
        }
    )
}