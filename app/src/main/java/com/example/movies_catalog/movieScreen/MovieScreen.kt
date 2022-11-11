package com.example.movies_catalog.movieScreen

import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.NotificationCompat.getColor
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import coil.compose.rememberAsyncImagePainter
import com.example.movies_catalog.R
import com.example.movies_catalog.mainScreen.galleryScreen.MainViewModel
import com.example.movies_catalog.network.models.Review
import com.example.movies_catalog.ui.theme.*
import com.google.accompanist.flowlayout.FlowRow

@Preview(showBackground = true)
@Composable
fun MovieScreen(){
    val movieViewModel = MovieViewModel()

    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        Poster(movieViewModel.movieDetails!!.poster, movieViewModel.movieDetails.name)
        Text(
            text = movieViewModel.movieDetails.description,
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
            color = White,
            fontSize = 14.sp,
            fontWeight = FontWeight.W400,
            textAlign = TextAlign.Start,
            lineHeight = 18.sp
        )
        Text(
            text = "О фильме",
            Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp),
            fontSize = 16.sp,
            fontWeight = FontWeight.W500,
            textAlign = TextAlign.Start,
            lineHeight = 20.sp,
            color = White
        )
        Description(movieViewModel)
        Text(
            text = "Жанры",
            Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp),
            fontSize = 16.sp,
            fontWeight = FontWeight.W500,
            textAlign = TextAlign.Start,
            lineHeight = 20.sp,
            color = White
        )
        FlowRow(
            Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp, 8.dp)
        ) {
            for (i in 0 until movieViewModel.movieDetails.genres.size){
                Genre(movieViewModel.movieDetails.genres[i].name)
            }
        }
        Row(
            Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp, 16.dp, 16.dp, 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Отзывы",
                fontSize = 16.sp,
                fontWeight = FontWeight.W500,
                textAlign = TextAlign.Start,
                lineHeight = 20.sp,
                color = White
            )
            Spacer(Modifier.weight(1f))
            IconButton(
                onClick = { /*TODO*/ }
            ) {
                Icon(
                    modifier = Modifier.padding(13.dp,13.dp,13.dp,13.dp),
                    tint = DarkRed,
                    painter = painterResource(R.drawable.plus_icon),
                    contentDescription = "Plus icon")
            }
        }
        for (i in 0 until movieViewModel.movieDetails.reviews.size){
            movieViewModel.parseDate(i)
            Review(movieViewModel.movieDetails.reviews[i], movieViewModel.reviewDate, movieViewModel.userId)
        }
    }
}

@Composable
fun Poster(image: String, name: String) {
    Box(modifier = Modifier.wrapContentSize()){
        Image(
            painter = rememberAsyncImagePainter(image),
            contentDescription = "Movie's Poster",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .drawWithCache {
                    val gradient = Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Black),
                        startY = size.height / 5,
                        endY = size.height
                    )
                    onDrawWithContent {
                        drawContent()
                        drawRect(gradient, blendMode = BlendMode.Multiply)
                    }
                }
        )
        Text(
            text = name,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp, 16.dp, 16.dp)
                .align(Alignment.BottomCenter),
            fontWeight = FontWeight.W700,
            fontSize = 36.sp,
            lineHeight = 40.sp,
            color = White
        )
    }
}

@Composable
fun Description(viewModel: MovieViewModel){
    Column(
        Modifier
            .fillMaxWidth()
            .padding(16.dp, 8.dp, 16.dp, 16.dp)
            .wrapContentHeight()
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Text(
                text = "Год",
                Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 0.dp, 8.dp, 0.dp)
                    .weight(0.34f),
                fontSize = 12.sp,
                fontWeight = FontWeight.W500,
                textAlign = TextAlign.Start,
                lineHeight = 14.sp,
                color = TextGray
            )
            Text(
                text = viewModel.movieDetails!!.year.toString(),
                Modifier
                    .fillMaxWidth()
                    .weight(0.66f),
                fontSize = 12.sp,
                fontWeight = FontWeight.W500,
                textAlign = TextAlign.Start,
                lineHeight = 14.sp,
                color = White
            )
        }
        Spacer(
            Modifier
                .fillMaxWidth()
                .height(4.dp))
        Row(
            Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Text(
                text = "Страна",
                Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 0.dp, 8.dp, 0.dp)
                    .weight(0.34f),
                fontSize = 12.sp,
                fontWeight = FontWeight.W500,
                textAlign = TextAlign.Start,
                lineHeight = 14.sp,
                color = TextGray
            )
            Text(
                text = viewModel.movieDetails!!.country,
                Modifier
                    .fillMaxWidth()
                    .weight(0.66f),
                fontSize = 12.sp,
                fontWeight = FontWeight.W500,
                textAlign = TextAlign.Start,
                lineHeight = 14.sp,
                color = White
            )
        }
        Spacer(
            Modifier
                .fillMaxWidth()
                .height(4.dp))
        Row(
            Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Text(
                text = "Время",
                Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 0.dp, 8.dp, 0.dp)
                    .weight(0.34f),
                fontSize = 12.sp,
                fontWeight = FontWeight.W500,
                textAlign = TextAlign.Start,
                lineHeight = 14.sp,
                color = TextGray
            )
            Text(
                text = viewModel.movieDetails!!.time.toString() + " мин.",
                Modifier
                    .fillMaxWidth()
                    .weight(0.66f),
                fontSize = 12.sp,
                fontWeight = FontWeight.W500,
                textAlign = TextAlign.Start,
                lineHeight = 14.sp,
                color = White
            )

        }
        Spacer(
            Modifier
                .fillMaxWidth()
                .height(4.dp))
        Row(
            Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Text(
                text = "Слоган",
                Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 0.dp, 8.dp, 0.dp)
                    .weight(0.34f),
                fontSize = 12.sp,
                fontWeight = FontWeight.W500,
                textAlign = TextAlign.Start,
                lineHeight = 14.sp,
                color = TextGray
            )
            Text(
                text = "\"" + viewModel.movieDetails!!.tagline + "\"",
                Modifier
                    .fillMaxWidth()
                    .weight(0.66f),
                fontSize = 12.sp,
                fontWeight = FontWeight.W500,
                textAlign = TextAlign.Start,
                lineHeight = 14.sp,
                color = White
            )
        }
        Spacer(
            Modifier
                .fillMaxWidth()
                .height(4.dp))
        Row(
            Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Text(
                text = "Режиссер",
                Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 0.dp, 8.dp, 0.dp)
                    .weight(0.34f),
                fontSize = 12.sp,
                fontWeight = FontWeight.W500,
                textAlign = TextAlign.Start,
                lineHeight = 14.sp,
                color = TextGray
            )
            Text(
                text = viewModel.movieDetails!!.director,
                Modifier
                    .fillMaxWidth()
                    .weight(0.66f),
                fontSize = 12.sp,
                fontWeight = FontWeight.W500,
                textAlign = TextAlign.Start,
                lineHeight = 14.sp,
                color = White
            )
        }
        Spacer(
            Modifier
                .fillMaxWidth()
                .height(4.dp))
        Row(
            Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Text(
                text = "Бюджет",
                Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 0.dp, 8.dp, 0.dp)
                    .weight(0.34f),
                fontSize = 12.sp,
                fontWeight = FontWeight.W500,
                textAlign = TextAlign.Start,
                lineHeight = 14.sp,
                color = TextGray
            )
            Text(
                text = "\$" + viewModel.showBudget,
                Modifier
                    .fillMaxWidth()
                    .weight(0.66f),
                fontSize = 12.sp,
                fontWeight = FontWeight.W500,
                textAlign = TextAlign.Start,
                lineHeight = 14.sp,
                color = White
            )
        }
        Spacer(
            Modifier
                .fillMaxWidth()
                .height(4.dp))
        Row(
            Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Text(
                text = "Сборы в мире",
                Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 0.dp, 8.dp, 0.dp)
                    .weight(0.34f),
                fontSize = 12.sp,
                fontWeight = FontWeight.W500,
                textAlign = TextAlign.Start,
                lineHeight = 14.sp,
                color = TextGray
            )
            Text(
                text = "\$" + viewModel.showFees,
                Modifier
                    .fillMaxWidth()
                    .weight(0.66f),
                fontSize = 12.sp,
                fontWeight = FontWeight.W500,
                textAlign = TextAlign.Start,
                lineHeight = 14.sp,
                color = White
            )
        }
        Spacer(
            Modifier
                .fillMaxWidth()
                .height(4.dp))
        Row(
            Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Text(
                text = "Возраст",
                Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 0.dp, 8.dp, 0.dp)
                    .weight(0.34f),
                fontSize = 12.sp,
                fontWeight = FontWeight.W500,
                textAlign = TextAlign.Start,
                lineHeight = 14.sp,
                color = TextGray
            )
            Text(
                text = viewModel.movieDetails!!.ageLimit.toString() + "+",
                Modifier
                    .fillMaxWidth()
                    .weight(0.66f),
                fontSize = 12.sp,
                fontWeight = FontWeight.W500,
                textAlign = TextAlign.Start,
                lineHeight = 14.sp,
                color = White
            )
        }
    }
}

@Composable
fun Genre(name: String) {
    Button(
        { },
        Modifier
            .wrapContentSize()
            .padding(0.dp, 0.dp, 8.dp, 0.dp),
        enabled = false,
        colors = ButtonDefaults.buttonColors(
            disabledBackgroundColor = DarkRed,
            disabledContentColor = White
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = name,
            //Modifier
            //    .fillMaxWidth(),
            fontSize = 12.sp,
            fontWeight = FontWeight.W500,
            textAlign = TextAlign.Center,
            lineHeight = 14.sp,
            color = White
        )
    }
}

@Composable
fun Review(review: Review, date: String, userId: String) {
    Card(
        Modifier
            .wrapContentSize()
            .padding(16.dp, 4.dp),
        shape = RoundedCornerShape(8.dp),
        backgroundColor = Black,
        border = BorderStroke(1.dp, Gray),
    ) {
        Column (Modifier.fillMaxSize()) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp, 0.dp)
            )
            {
                Avatar(review.author?.avatar, review.isAnonymous)
                Column(
                    Modifier
                        .fillMaxHeight()
                        .padding(8.dp, 0.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = if (review.isAnonymous) "Анонимный пользователь" else review.author!!.nickName,
                        color = White,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Left,
                        fontWeight = FontWeight.W500,
                        lineHeight = 20.sp
                    )
                    if (review.author?.userId == userId) {
                        Text(
                            text = "мой отзыв",
                            color = TextGray,
                            fontSize = 12.sp,
                            textAlign = TextAlign.Left,
                            fontWeight = FontWeight.W400,
                            lineHeight = 14.sp
                        )
                    }
                }
                Spacer(Modifier.weight(1f))
                //val ratio: Float = review.rating * 0.1
                val color = ColorUtils.blendARGB(Red.toArgb(), Green.toArgb(), review.rating * 0.1f)
                Button(
                    { },
                    Modifier
                        .wrapContentSize()
                        .padding(0.dp, 6.dp),
                    enabled = false,
                    colors = ButtonDefaults.buttonColors(
                        disabledBackgroundColor = Color(color),
                        disabledContentColor = White
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = review.rating.toString(),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W500,
                        textAlign = TextAlign.Center,
                        lineHeight = 20.sp,
                        color = White
                    )
                }
            }
            Text(
                text = review.reviewText,
                Modifier
                    .fillMaxWidth()
                    .padding(8.dp, 0.dp),
                fontSize = 14.sp,
                fontWeight = FontWeight.W400,
                textAlign = TextAlign.Start,
                lineHeight = 18.sp,
                color = White
            )
            Text(
                text = date,
                Modifier
                    .padding(8.dp, 4.dp, 8.dp, 8.dp)
                    .fillMaxWidth(),
                fontSize = 12.sp,
                fontWeight = FontWeight.W400,
                textAlign = TextAlign.Start,
                lineHeight = 14.sp,
                color = TextGray
            )
        }
    }
}

@Composable
fun Avatar(image: String?, isAnonymous: Boolean){

    val avatar: String =
        if (image == "" || isAnonymous || image == null) { stringResource(R.string.default_avatar) }
        else { image }

    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(White)
    ) {
        Image(
            painter = rememberAsyncImagePainter(avatar),
            contentDescription = "avatar",
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center
        )
    }
}