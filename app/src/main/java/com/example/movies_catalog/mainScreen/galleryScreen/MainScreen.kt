package com.example.movies_catalog.mainScreen.galleryScreen

import android.view.View
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.movies_catalog.ui.theme.Black
import com.example.movies_catalog.ui.theme.DarkRed
import com.example.movies_catalog.ui.theme.White

@Composable
fun MainScreen(openMovieDescription: () -> Unit) {

    val mainViewModel: MainViewModel = viewModel()

    mainViewModel.resizeMoviesList()

    val moviesListSize : Int by remember { mainViewModel.moviesListSize }
    val favListSize : Int by remember { mainViewModel.favListSize }

    LazyColumn(
        Modifier
            .fillMaxSize()
            .background(Black)
    ){
        item {
            FirstMovieCard(
                mainViewModel
            ) {openMovieDescription()}
        }
        item {
            if (favListSize != 0){
                FavoritesList()
            }
        }
        item {
            Text(
                text = "Галерея",
                color = DarkRed,
                fontSize = 24.sp,
                fontWeight = FontWeight.W700,
                lineHeight = 32.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 16.dp, 0.dp, 0.dp)
            )
        }
        itemsIndexed(
            items = mainViewModel.movies!!.movies
        ) { index, item ->
            if (index == moviesListSize - 1 && (mainViewModel.page + 1) != moviesListSize) {
                mainViewModel.page = mainViewModel.page + 1
                mainViewModel.getMovies()
            }

            if (index != 0) {
                mainViewModel.getGenresString(index)
                val genres = mainViewModel.genres
                MovieCard(
                    viewModel = mainViewModel,
                    id = item.id,
                    title = item.name,
                    year = item.year,
                    country = item.country,
                    image = item.poster,
                    genres = genres
                ) { openMovieDescription() }
            }
        }
    }
}

@Composable
fun FirstMovieCard(
    viewModel: MainViewModel,
    openMovieDescription: () -> Unit
) {
    Box(modifier = Modifier.wrapContentSize()){
        Image(
            painter = rememberAsyncImagePainter(viewModel.movies!!.movies[0].poster),
            contentDescription = "Movie's Poster",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(320.dp)
                .drawWithCache {
                    val gradient = Brush.verticalGradient(
                        colors = listOf(Transparent, Black),
                        startY = size.height / 5,
                        endY = size.height
                    )
                    onDrawWithContent {
                        drawContent()
                        drawRect(gradient, blendMode = BlendMode.Multiply)
                    }
                }
        )
        Button(
            onClick = { viewModel.getMovieDetails(viewModel.movies.movies[0].id) { openMovieDescription() } },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(0.dp, 0.dp, 0.dp, 32.dp)
                .height(44.dp)
                .width(160.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = DarkRed,
                contentColor = White
            ),
            shape = RoundedCornerShape(8.dp)
        )
        {
            Text("Смотреть", fontSize = 16.sp)
        }
    }
}

@Composable
fun FavoriteMovieCard(image: String, openMovieDescription: () -> Unit){
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
fun FavoritesList(){
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
}

//тут есть странный отступ между началом карточки и картинкой
@Composable
fun MovieCard(
    viewModel: MainViewModel,
    id: String,
    title: String,
    year: Int,
    country: String,
    image: String,
    genres: String,
    openMovieDescription: () -> Unit
) {
    Card(
        Modifier
            .wrapContentSize()
            .padding(16.dp, 8.dp)
            .clickable { viewModel.getMovieDetails(id) { openMovieDescription() } },
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
                    .fillMaxHeight()
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
