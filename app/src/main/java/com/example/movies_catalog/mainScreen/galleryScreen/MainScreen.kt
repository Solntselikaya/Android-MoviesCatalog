package com.example.movies_catalog.mainScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.movies_catalog.R
import com.example.movies_catalog.mainScreen.galleryScreen.MainViewModel
import com.example.movies_catalog.ui.theme.DarkRed

@Preview(showBackground = true)
@Composable
fun MainScreen() {
    val mainViewModel: MainViewModel = viewModel()


    Column(modifier = Modifier.fillMaxSize()){
        Box(modifier = Modifier.wrapContentSize()){}
        Text(
            text = "Избранное",
            color = DarkRed,
            fontSize = 24.sp,
            fontWeight = FontWeight.W700,
            lineHeight = 32.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 32.dp, 0.dp, 0.dp)
        )
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize()
                .height(172.dp)
                .padding(16.dp, 8.dp, 0.dp, 16.dp)
        ) {
            //add items later
        }
        LazyColumn(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            item {
                Text(
                    text = "Галерея",
                    color = DarkRed,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.W700,
                    lineHeight = 32.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 0.dp, 0.dp, 8.dp)
                )
            }
            //add items later
        }
    }
}

@Composable
fun FavoriteMovieCard(image: String){
    Box(modifier = Modifier.fillMaxHeight()
    ) {
        Image(
            painter = rememberAsyncImagePainter(image),
            contentDescription = "Movie's Poster",
            contentScale = ContentScale.FillHeight,
            modifier = Modifier
                .fillMaxHeight()
        )
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