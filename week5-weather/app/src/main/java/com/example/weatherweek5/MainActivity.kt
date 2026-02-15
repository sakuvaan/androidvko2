package com.example.weatherweek5

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.weatherweek5.ui.WeatherScreen
import com.example.weatherweek5.ui.theme.WeatherWeek5Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherWeek5Theme {
                WeatherScreen()
            }
        }
    }
}
