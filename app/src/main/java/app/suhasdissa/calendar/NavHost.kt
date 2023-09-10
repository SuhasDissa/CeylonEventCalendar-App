package app.suhasdissa.calendar

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import app.suhasdissa.calendar.ui.screens.AddEventScreen
import app.suhasdissa.calendar.ui.screens.CategoryScreen
import app.suhasdissa.calendar.ui.screens.HomeScreen
import app.suhasdissa.calendar.ui.screens.SearchScreen
import app.suhasdissa.calendar.ui.screens.settings.AboutScreen
import app.suhasdissa.calendar.ui.screens.settings.SettingsScreen

@Composable
fun AppNavHost(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = Destination.Home.route
    ) {
        composable(route = Destination.Home.route) {
            HomeScreen(onNavigate = {
                navHostController.navigateTo(it.route)
            })
        }
        composable(route = Destination.About.route) {
            AboutScreen()
        }
        composable(route = Destination.Settings.route) {
            SettingsScreen {
                navHostController.navigateTo(Destination.About.route)
            }
        }
        composable(route = Destination.Search.route) {
            SearchScreen()
        }
        composable(route = Destination.Category.route) {
            CategoryScreen()
        }
        composable(route = Destination.AddEvent.route) {
            AddEventScreen()
        }
    }
}

fun NavHostController.navigateTo(route: String) = this.navigate(route) {
    launchSingleTop = true
    restoreState = true
}
