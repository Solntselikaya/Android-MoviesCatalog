package com.example.movies_catalog.navigation

sealed class Screens(val route: String) {
    object SignInScreen : Screens("sign_in_screen")
    object SignUpScreen : Screens("sign_up_screen")
    object NavBarScreen : Screens("navbar_screen")
    object MovieScreen : Screens("movie_screen")
}