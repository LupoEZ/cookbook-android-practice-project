package com.ericz.cookbook.testutils

import com.ericz.cookbook.data.entities.Instruction
import com.ericz.cookbook.data.entities.Recipe

object TestData {

    val INSTRUCTION_RECIPE_ID_1 = listOf(
        Instruction(id = 1, step = "Preheat oven to 180°C", stepNumber = 1, recipeId = 1),
        Instruction(id = 2, step = "Mix ingredients", stepNumber = 2, recipeId = 1),
        Instruction(id = 3, step = "Bake for 30 minutes", stepNumber = 3, recipeId = 1)
    )

    val INSTRUCTION_RECIPE_ID_2 = listOf(
        Instruction(id = 4, step = "Boil water", stepNumber = 1, recipeId = 2),
        Instruction(id = 5, step = "Add pasta", stepNumber = 2, recipeId = 2),
        Instruction(id = 6, step = "Drain and serve", stepNumber = 3, recipeId = 2)
    )

    val RECIPE_ID_1 = Recipe(
        id = 1,
        title = "Chocolate Chip Cookies",
        description = "Nice Chocolate Chip Cookies out of the oven"
    )
}