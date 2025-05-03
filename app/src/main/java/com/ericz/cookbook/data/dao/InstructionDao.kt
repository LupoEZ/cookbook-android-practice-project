package com.ericz.cookbook.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ericz.cookbook.data.entities.Instruction

@Dao
interface InstructionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInstructions(instructions: List<Instruction>)

    @Query("SELECT * FROM instructions WHERE recipeId = :recipeId ORDER BY stepNumber")
    suspend fun getInstructionsForRecipe(recipeId: Int): List<Instruction>
}
