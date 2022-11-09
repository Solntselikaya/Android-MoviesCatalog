package com.example.movies_catalog.mainScreen.galleryScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.movies_catalog.ui.theme.Black
import com.example.movies_catalog.ui.theme.DarkRed
import com.example.movies_catalog.ui.theme.Gray
import com.example.movies_catalog.ui.theme.White

@Preview(showBackground = true)
@Composable
fun MainScreen() {
    val mainViewModel: MainViewModel = viewModel()

    mainViewModel.resizeMoviesList()

    val moviesListSize : Int by remember { mainViewModel.moviesListSize }
    val favListSize : Int by remember { mainViewModel.favListSize }

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
            items(moviesListSize) { index ->
                mainViewModel.getGenresString(index)
                val genres = mainViewModel.genres
                MovieCard(
                    title = mainViewModel.movies!!.movies[index].name,
                    year = mainViewModel.movies.movies[index].year,
                    country = mainViewModel.movies.movies[index].country,
                    image = mainViewModel.movies.movies[index].poster,
                    genres = genres
                )
            }
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

//тут есть странный отступ между началом карточки и картинкой
@Composable
fun MovieCard(title: String, year: Int, country: String, image: String, genres: String) {
    Card(
        Modifier
            .wrapContentSize()
            .padding(0.dp, 8.dp)
            .clickable { },
        backgroundColor = Black
    ) {
        Row(
            Modifier
                .fillMaxWidth()
        ) {
            Image(
                painter = rememberAsyncImagePainter(image),
                contentDescription = "Movie's Poster",
                modifier = Modifier
                    .height(144.dp)
                    .weight(0.3f)
            )
            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(16.dp, 0.dp, 0.dp, 0.dp)
                    .weight(0.7f),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = title,
                    color = White,
                    fontWeight = FontWeight.W700,
                    fontSize = 20.sp,
                    lineHeight = 24.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "$year • $country",
                    color = White,
                    fontWeight = FontWeight.W700,
                    fontSize = 14.sp,
                    lineHeight = 18.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = genres,
                    color = White,
                    fontWeight = FontWeight.W700,
                    fontSize = 14.sp,
                    lineHeight = 18.sp,
                    overflow = TextOverflow.Visible
                )
            }
        }
    }
}