package com.ericz.cookbook.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ericz.cookbook.data.entities.Recipe

@Composable
fun RecipeListScreen(
    recipes: List<Recipe>,
    onAddRecipe: () -> Unit,
    onAddIngredient: () -> Unit,
    onRecipeClick: (Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            Box {
                FloatingActionButton(onClick = { expanded = !expanded }) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        onClick = {
                            expanded = false
                            onAddRecipe()
                        },
                        text = { Text("Add Recipe") }
                    )
                    DropdownMenuItem(
                        onClick = {
                            expanded = false
                            onAddIngredient()
                        },
                        text = { Text("Add Ingredient") }
                    )
                }
            }
        }
    ) { innerPadding ->
        if (recipes.isEmpty()) {
            Text(
                text = "No recipes yet! Add some.",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(16.dp)
            )
        } else {
            LazyColumn(
                modifier = Modifier.padding(innerPadding),
                verticalArrangement = Arrangement.spacedBy(8.dp) // Added spacing
            ) {
                items(recipes) { recipe ->
                    Text(
                        text = recipe.title,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                            .clickable { onRecipeClick(recipe.id) }
                    )
                }
            }
        }
    }
}
