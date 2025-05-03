package com.ericz.cookbook.app

import android.app.Application
import com.ericz.cookbook.data.database.CookbookDatabase

class CookbookApplication : Application() {
    lateinit var database: CookbookDatabase

    override fun onCreate() {
        super.onCreate()
        database = CookbookDatabase.getInstance(this)
    }
}