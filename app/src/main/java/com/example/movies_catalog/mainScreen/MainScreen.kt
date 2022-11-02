package com.example.movies_catalog.mainScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.movies_catalog.R

@Composable
fun MainScreen() {
    Box(modifier = Modifier.fillMaxSize()){
        Column(modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally){

        }
        BottomNavigationBar()
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavigationBar() {
    val items = listOf(
        NavigationItem.Main,
        NavigationItem.Profile
    )
    BottomNavigation(
        backgroundColor = colorResource(R.color.black),
        contentColor = colorResource(R.color.gray)
    ) {
        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(painterResource(item.icon), contentDescription = item.title) },
                label = { Text(text = item.title) },
                selectedContentColor = colorResource(R.color.dark_red),
                unselectedContentColor = colorResource(R.color.gray),
                alwaysShowLabel = true,
                selected = false,
                onClick = {
                    /* Add code later */
                }
            )
        }
    }
}