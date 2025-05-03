package com.ericz.cookbook.testutils

import android.content.Context
import androidx.room.Room
import com.ericz.cookbook.data.database.CookbookDatabase

object TestDatabaseProvider {

    fun createTestDatabase(context: Context): CookbookDatabase {
        return Room.inMemoryDatabaseBuilder(
            context,
            CookbookDatabase::class.java
        )
            .allowMainThreadQueries() // Allow queries on the main thread for testing purposes
            .build()
    }
}