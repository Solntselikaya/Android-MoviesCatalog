package com.example.movies_catalog.screens.mainScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.movies_catalog.navigation.BottomNavBarNavigation
import com.example.movies_catalog.navigation.BottomNavItem
import com.example.movies_catalog.ui.theme.DarkRed
import com.example.movies_catalog.ui.theme.Gray
import com.example.movies_catalog.ui.theme.MostlyBlack

@Composable
fun NavBarScreen(logout: () -> Unit, movieDescription: () -> Unit) {

    val navController = rememberNavController()

    Scaffold(
        topBar = {},
        bottomBar = { BottomNavBar(navController) },
        content = { padding ->
            Box(modifier = Modifier.padding(padding)) {
                BottomNavBarNavigation(
                    navController = navController,
                    { logout() },
                    { movieDescription() })
            }
        },
        backgroundColor = Black
    )
}

@Composable
fun BottomNavBar(navController: NavController) {

    val items = listOf(
        BottomNavItem.Main,
        BottomNavItem.Profile
    )

    BottomNavigation(
        Modifier.height(68.dp),
        backgroundColor = MostlyBlack,
        contentColor = Gray
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        painterResource(item.icon),
                        contentDescription = item.title,
                        Modifier.padding(0.dp, 11.dp, 0.dp, 7.dp)
                    )
                },
                label = {
                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.button
                    )
                },
                selectedContentColor = DarkRed,
                unselectedContentColor = Gray,
                alwaysShowLabel = true,
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        navController.graph.startDestinationRoute?.let {
                            popUpTo("main") {
                                inclusive = false
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}