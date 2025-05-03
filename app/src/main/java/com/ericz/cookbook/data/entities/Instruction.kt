package com.ericz.cookbook.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "instructions",
    foreignKeys = [
        ForeignKey(
            entity = Recipe::class,
            parentColumns = ["id"],
            childColumns = ["recipeId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Instruction(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val step: String,
    val stepNumber: Int,
    val recipeId: Int
)
