package com.ericz.cookbook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.ericz.cookbook.app.CookbookApplication
import com.ericz.cookbook.navigation.AppNavigation
import com.ericz.cookbook.ui.screens.AddRecipeScreen
import com.ericz.cookbook.ui.theme.CookbookTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = (application as CookbookApplication).database

        enableEdgeToEdge()
        setContent {
            CookbookTheme {
                AppNavigation()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewWindow() {
    CookbookTheme {
        AppNavigation()
    }
}