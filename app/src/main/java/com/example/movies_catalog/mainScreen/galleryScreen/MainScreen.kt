package com.example.movies_catalog.mainScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.movies_catalog.R

@Preview(showBackground = true)
@Composable
fun MainScreen() {

    Box(modifier = Modifier.fillMaxSize()){
        Column(modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally){
        }
    }
}

@Composable
fun MovieCards(title: String, year: Int, country: String, image: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            Modifier
                .fillMaxWidth()
                .height(144.dp)
                .clickable { },
            backgroundColor = colorResource(R.color.white),
        ) {
            Row(
                modifier = Modifier
                    .matchParentSize()
                    .background(color = colorResource(R.color.black))
            )
            {
                Image(
                    painter = rememberAsyncImagePainter(image),
                    contentDescription = "Movie's Poster",
                    contentScale = ContentScale.FillHeight,
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(0.3f)
                )
            }
        }
    }
}