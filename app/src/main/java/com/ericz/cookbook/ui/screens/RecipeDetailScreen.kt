package com.ericz.cookbook.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ericz.cookbook.viewmodel.RecipeDetailViewModel

@Composable
fun RecipeDetailScreen(
    recipeId: Int,
    viewModel: RecipeDetailViewModel
) {
    val recipeDetails by viewModel.recipeDetails.observeAsState()

    LaunchedEffect(recipeId) {
        viewModel.loadRecipeDetails(recipeId)
    }

    Scaffold { innerPadding ->
        when (recipeDetails) {
            null -> {
                Text("Loading recipe details...")
            }
            else -> {
                val recipe = recipeDetails!!.recipe
                val ingredients = recipeDetails!!.ingredients
                val instructions = recipeDetails!!.instructions

                LazyColumn(
                    modifier = Modifier.padding(innerPadding).padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp) // Added spacing between items
                ) {
                    item {
                        Text(
                            text = recipe.title,
                            style = MaterialTheme.typography.headlineMedium,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }
                    item {
                        Text(
                            text = recipe.description,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                    }
                    item { Text(text = "Ingredients", style = MaterialTheme.typography.headlineSmall) }
                    items(ingredients) { ingredient ->
                        Text(
                            text = "${ingredient.amount}x ${ingredient.unit} ${ingredient.name}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    item { Spacer(modifier = Modifier.padding(16.dp)) }
                    item { Text(text = "Instructions", style = MaterialTheme.typography.headlineSmall) }
                    items(instructions) { instruction ->
                        Text(
                            text = "${instruction.stepNumber}. ${instruction.step}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}
