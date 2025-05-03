package com.ericz.cookbook.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.ericz.cookbook.data.dao.IngredientDao
import com.ericz.cookbook.data.dao.InstructionDao
import com.ericz.cookbook.data.dao.RecipeDao
import com.ericz.cookbook.data.dao.RecipeIngredientDao
import com.ericz.cookbook.data.entities.Ingredient
import com.ericz.cookbook.data.entities.Instruction
import com.ericz.cookbook.data.entities.Recipe
import com.ericz.cookbook.data.entities.RecipeIngredient

@Database(
    entities = [Recipe::class, Ingredient::class, Instruction::class, RecipeIngredient::class],
    version = 3,
    exportSchema = true
)
abstract class CookbookDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
    abstract fun ingredientDao(): IngredientDao
    abstract fun instructionDao(): InstructionDao
    abstract fun recipeIngredientDao(): RecipeIngredientDao

    companion object {
        @Volatile
        private var INSTANCE: CookbookDatabase? = null

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE ingredients ADD COLUMN calories REAL NOT NULL DEFAULT 0")
                db.execSQL("ALTER TABLE ingredients ADD COLUMN fat REAL NOT NULL DEFAULT 0")
                db.execSQL("ALTER TABLE ingredients ADD COLUMN carbs REAL NOT NULL DEFAULT 0")
                db.execSQL("ALTER TABLE ingredients ADD COLUMN protein REAL NOT NULL DEFAULT 0")
            }
        }

        private val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE recipe_ingredients ADD COLUMN amount REAL NOT NULL DEFAULT 1")
            }
        }

        fun getInstance(context: Context): CookbookDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CookbookDatabase::class.java,
                    "cookbook_database"
                )
                .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
