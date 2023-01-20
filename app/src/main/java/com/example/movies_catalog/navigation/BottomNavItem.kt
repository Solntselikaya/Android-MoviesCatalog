package com.example.movies_catalog.navigation

import com.example.movies_catalog.R

sealed class BottomNavItem(var route: String, var icon: Int, var title: String) {
    object Main : BottomNavItem("main", R.drawable.tv_icon, "Главное")
    object Profile : BottomNavItem("profile", R.drawable.profile_icon, "Профиль")
}
