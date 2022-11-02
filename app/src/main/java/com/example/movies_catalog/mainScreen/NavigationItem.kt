package com.example.movies_catalog.mainScreen

import com.example.movies_catalog.R

sealed class NavigationItem(var route: String, var icon: Int, var title: String) {
    object Main: NavigationItem("main", R.drawable.tv_icon, "Главное")
    object Profile: NavigationItem("profile", R.drawable.profile_icon, "Профиль")
}
