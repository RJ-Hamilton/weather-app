package com.hamilton.weatherapp

import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hamilton.weatherapp.forecast.ForecastScreen
import com.hamilton.weatherapp.ui.FORECAST_BUTTON_INDEX
import com.hamilton.weatherapp.ui.HOME_BUTTON_INDEX
import com.hamilton.weatherapp.landing.LandingScreen
import com.hamilton.weatherapp.ui.WeatherBottomNavBar
import kotlinx.serialization.Serializable

@Serializable
object LandingScreen

@Serializable
object ForecastScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    val modifier = Modifier.navigationBarsPadding()
    val context = LocalContext.current

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            WeatherBottomNavBar(
                onItemSelected = { newSelectedIndex ->
                    when(newSelectedIndex) {
                        HOME_BUTTON_INDEX -> setupBottomNavigation(
                            route = LandingScreen,
                            navController = navController
                        )
                        FORECAST_BUTTON_INDEX -> setupBottomNavigation(
                            route = ForecastScreen,
                            navController = navController
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        NavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            startDestination = LandingScreen
        ) {
            composable<LandingScreen> {
                BackHandler(enabled = true) {
                    context as ComponentActivity
                    context.finish()
                }
                LandingScreen()
            }
            composable<ForecastScreen> {
                BackHandler(enabled = true) {
                    context as ComponentActivity
                    context.finish()
                }
                ForecastScreen()
            }
        }
    }
}

private fun setupBottomNavigation(route: Any, navController: NavController) {
    navController.navigate(route) {
        popUpTo(navController.graph.startDestinationId) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}