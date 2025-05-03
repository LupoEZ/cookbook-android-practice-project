package com.ericz.cookbook.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ericz.cookbook.viewmodel.AddRecipeViewModel

@Composable
fun AddRecipeScreen(
    viewModel: AddRecipeViewModel = viewModel(),
    onRecipeSaved: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var showAddIngredientDialog by remember { mutableStateOf(false) }

    Scaffold { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Recipe Title
            item {
                TextField(
                    value = viewModel.title,
                    onValueChange = viewModel::updateTitle,
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // Recipe Description
            item {
                TextField(
                    value = viewModel.description,
                    onValueChange = viewModel::updateDescription,
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // Ingredient Search
            item {
                TextField(
                    value = searchQuery,
                    onValueChange = { query ->
                        searchQuery = query
                        viewModel.filterIngredients(query) // Updates filtered list in ViewModel
                    },
                    label = { Text("Search Ingredients") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // Selected Ingredients
            if (viewModel.selectedIngredients.isNotEmpty()) {
                item {
                    Text("Selected Ingredients", style = MaterialTheme.typography.bodyMedium)
                }
                items(viewModel.selectedIngredients) { selectedIngredient ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("${selectedIngredient.ingredient.name} (${selectedIngredient.ingredient.unit})")

                        // Quantity Input
                        TextField(
                            value = selectedIngredient.amount.toString(),
                            onValueChange = { amountStr ->
                                val amount = amountStr.toFloatOrNull() ?: 1f // Default to 1 if invalid
                                viewModel.updateSelectedIngredientAmount(selectedIngredient.ingredient.id, amount)
                            },
                            label = { Text("Qty") },
                            modifier = Modifier.width(80.dp)
                        )

                        IconButton(onClick = { viewModel.deselectIngredient(selectedIngredient.ingredient) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Remove Ingredient")
                        }
                    }
                }
            }

            // Filtered Ingredients
            if (searchQuery.isNotEmpty()) {
                item {
                    Text("Ingredient Suggestions", style = MaterialTheme.typography.bodyMedium)
                }
                items(viewModel.filteredIngredients) { ingredient ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("${ingredient.name} (${ingredient.unit})")
                        IconButton(onClick = { viewModel.selectIngredient(ingredient) }) {
                            Icon(Icons.Default.Add, contentDescription = "Add Ingredient")
                        }
                    }
                }
            }

            // Add New Ingredient Button
            item {
                Button(onClick = { showAddIngredientDialog = true }) {
                    Text("Add New Ingredient")
                }
            }

            // Add New Ingredient Dialog
            if (showAddIngredientDialog) {
                item {
                    AddIngredientDialog(
                        onDismiss = { showAddIngredientDialog = false },
                        onAddIngredient = { newIngredient ->
                            viewModel.addIngredient(newIngredient)
                            showAddIngredientDialog = false
                        }
                    )
                }
            }

            // Instructions Section
            item {
                Text("Instructions")
            }
            items(viewModel.instructions) { instruction ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("${instruction.stepNumber}.", modifier = Modifier.padding(end = 8.dp))
                    TextField(
                        value = instruction.step,
                        onValueChange = { updatedStep ->
                            viewModel.updateInstruction(instruction.stepNumber - 1, updatedStep)
                        },
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(onClick = {
                        viewModel.removeInstruction(instruction.stepNumber - 1)
                    }) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete Instruction")
                    }
                }
            }

            // Add Instruction Button
            item {
                Button(
                    onClick = viewModel::addInstruction,
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Text("Add Instruction")
                }
            }

            // Save Recipe Button
            item {
                Spacer(modifier = Modifier.padding(16.dp))
                Button(onClick = {
                    viewModel.saveRecipe()
                    onRecipeSaved()
                }) {
                    Text("Save Recipe")
                }
            }
        }
    }
}

