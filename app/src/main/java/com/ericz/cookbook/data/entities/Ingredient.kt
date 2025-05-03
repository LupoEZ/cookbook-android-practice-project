package com.ericz.cookbook.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ingredients")
data class Ingredient(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val unit: String,
    val calories: Float,
    val fat: Float,
    val carbs: Float,
    val protein: Float
)