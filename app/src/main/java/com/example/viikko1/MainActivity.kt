package com.example.viikko1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*
import com.example.viikko1.ui.theme.Viikko1Theme
import com.example.viikko1.view.CalendarScreen
import com.example.viikko1.view.HomeScreen
import com.example.viikko1.view.Routes
import com.example.viikko1.viewmodel.TaskViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Viikko1Theme {

                val navController = rememberNavController()
                val vm: TaskViewModel = viewModel()

                NavHost(
                    navController = navController,
                    startDestination = Routes.HOME
                ) {
                    composable(Routes.HOME) {
                        HomeScreen(
                            vm = vm,
                            goCalendar = { navController.navigate(Routes.CALENDAR) }
                        )
                    }
                    composable(Routes.CALENDAR) {
                        CalendarScreen(
                            vm = vm,
                            goHome = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}
