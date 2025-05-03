package com.ericz.cookbook.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ericz.cookbook.data.database.CookbookDatabase
import com.ericz.cookbook.data.repositories.IngredientRepository
import com.ericz.cookbook.data.repositories.InstructionRepository
import com.ericz.cookbook.data.repositories.RecipeIngredientRepository
import com.ericz.cookbook.data.repositories.RecipeRepository
import com.ericz.cookbook.ui.screens.AddIngredientScreen
import com.ericz.cookbook.ui.screens.AddRecipeScreen
import com.ericz.cookbook.ui.screens.RecipeDetailScreen
import com.ericz.cookbook.ui.screens.RecipeListScreen
import com.ericz.cookbook.viewmodel.AddIngredientViewModel
import com.ericz.cookbook.viewmodel.AddRecipeViewModel
import com.ericz.cookbook.viewmodel.GenericViewModelFactory
import com.ericz.cookbook.viewmodel.RecipeViewModel
import com.ericz.cookbook.viewmodel.provideRecipeDetailViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    val context = LocalContext.current
    val recipeDao = CookbookDatabase.getInstance(context).recipeDao()
    val ingredientDao = CookbookDatabase.getInstance(context).ingredientDao()
    val instructionDao = CookbookDatabase.getInstance(context).instructionDao()
    val recipeIngredientDao = CookbookDatabase.getInstance(context).recipeIngredientDao()

    val recipeRepository = RecipeRepository(recipeDao)
    val instructionRepository = InstructionRepository(instructionDao)
    val ingredientRepository = IngredientRepository(ingredientDao)
    val recipeIngredientRepository = RecipeIngredientRepository(recipeIngredientDao)

    val recipeViewModel: RecipeViewModel = viewModel(
        factory = GenericViewModelFactory(
            RecipeViewModel::class.java
        ) {
            RecipeViewModel(recipeRepository)
        }
    )

    val recipes by recipeViewModel.recipes.collectAsState()

    NavHost(navController = navController, startDestination = "recipe_list") {
        composable("recipe_list") {
            RecipeListScreen(
                recipes = recipes,
                onAddRecipe = { navController.navigate("add_recipe") },
                onAddIngredient = { navController.navigate("add_ingredient") },
                onRecipeClick = { recipeId ->
                    navController.navigate("recipe_detail/$recipeId")
                }
            )
        }
        composable("add_recipe") {
            val addRecipeViewModel: AddRecipeViewModel = viewModel(
                factory = GenericViewModelFactory(AddRecipeViewModel::class.java) {
                    AddRecipeViewModel(
                        recipeRepository,
                        ingredientRepository,
                        instructionRepository,
                        recipeIngredientRepository
                    )
                }
            )
            AddRecipeScreen(
                viewModel = addRecipeViewModel,
                onRecipeSaved = {
                    navController.popBackStack()
                }
            )
        }

        composable("add_ingredient") {
            val addIngredientViewModel: AddIngredientViewModel = viewModel(
                factory = GenericViewModelFactory(AddIngredientViewModel::class.java) {
                    AddIngredientViewModel(ingredientRepository)
                }
            )
            AddIngredientScreen(
                viewModel = addIngredientViewModel,
                onIngredientSaved = {
                    navController.popBackStack()
                }
            )
        }

        composable("recipe_detail/{recipeId}") { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getString("recipeId")?.toIntOrNull()
            if (recipeId != null) {
                val recipeDetailViewModel = provideRecipeDetailViewModel(context)
                RecipeDetailScreen(recipeId = recipeId, viewModel = recipeDetailViewModel)
            } else {
                Text("Invalid recipe ID")
            }
        }
    }
}