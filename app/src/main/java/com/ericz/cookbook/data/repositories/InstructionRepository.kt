package com.ericz.cookbook.data.repositories

import com.ericz.cookbook.data.dao.InstructionDao
import com.ericz.cookbook.data.entities.Instruction

class InstructionRepository(private val instructionDao: InstructionDao) {
    suspend fun addInstructions(instructions: List<Instruction>) {
        instructionDao.insertInstructions(instructions)
    }

    suspend fun getInstructionsForRecipe(recipeId: Int): List<Instruction> {
        return instructionDao.getInstructionsForRecipe(recipeId)
    }
}
