package com.example.viikko1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.viikko1.view.HomeScreen
import com.example.viikko1.ui.theme.Viikko1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Viikko1Theme {
                HomeScreen()
            }
        }
    }
}
