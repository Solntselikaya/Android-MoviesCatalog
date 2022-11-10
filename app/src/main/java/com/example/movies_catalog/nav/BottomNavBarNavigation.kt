package com.example.movies_catalog.nav.navBar

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.movies_catalog.mainScreen.galleryScreen.MainScreen
import com.example.movies_catalog.mainScreen.profileScreen.ProfileScreen
import com.example.movies_catalog.nav.BottomNavItem

@Composable
fun BottomNavBarNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = BottomNavItem.Main.route
    ){

        composable(BottomNavItem.Main.route) {
            MainScreen()
        }

        composable(BottomNavItem.Profile.route) {
            ProfileScreen()
        }
    }
}