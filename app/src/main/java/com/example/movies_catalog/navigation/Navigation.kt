package com.example.movies_catalog.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.movies_catalog.screens.mainScreen.NavBarScreen
import com.example.movies_catalog.screens.mainScreen.galleryScreen.MainScreen
import com.example.movies_catalog.screens.mainScreen.profileScreen.ProfileScreen
import com.example.movies_catalog.screens.movieScreen.MovieScreen
import com.example.movies_catalog.screens.signInScreen.SignInScreen
import com.example.movies_catalog.screens.signUpScreen.SignUpScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screens.SignInScreen.route, //проверка, залогинен ли пользователь ннада!!
    ) {
        composable(route = Screens.SignInScreen.route) {
            SignInScreen(navController = navController)
        }

        composable(route = Screens.SignUpScreen.route) {
            SignUpScreen(navController = navController)
        }

        composable(route = Screens.MovieScreen.route) {
            MovieScreen()
        }

        composable(route = Screens.NavBarScreen.route) {
            NavBarScreen(
                {
                    navController.navigate(Screens.SignInScreen.route) {
                        popUpTo(Screens.SignInScreen.route) {
                            saveState = false
                            inclusive = true
                        }
                        restoreState = false
                        launchSingleTop = true
                    }
                },
                { navController.navigate(Screens.MovieScreen.route) }
            )
        }
    }
}

@Composable
fun BottomNavBarNavigation(
    navController: NavHostController,
    logout: () -> Unit,
    movieDescription: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = BottomNavItem.Main.route
    ) {

        composable(BottomNavItem.Main.route) {
            MainScreen { movieDescription() }
        }

        composable(BottomNavItem.Profile.route) {
            ProfileScreen { logout() }
        }
    }
}