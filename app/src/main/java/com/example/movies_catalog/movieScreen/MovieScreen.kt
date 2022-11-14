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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
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
import com.example.movies_catalog.network.models.ReviewShort
import com.example.movies_catalog.ui.theme.*
import com.google.accompanist.flowlayout.FlowRow

@Preview(showBackground = true)
@Composable
fun MovieScreen(){

    val movieViewModel = MovieViewModel()

    reviewDialog(viewModel = movieViewModel)

    val postedReview: Boolean by remember {movieViewModel.postedReview}
    val postedReviewNum: Int by remember {movieViewModel.postedReviewNum}

    //movieViewModel.checkReviews()

    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        movieViewModel.checkReviews()
        Poster(movieViewModel.movieDetails!!.poster, movieViewModel!!.movieDetails!!.name)
        Text(
            text = movieViewModel!!.movieDetails!!.description,
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
            for (i in 0 until movieViewModel!!.movieDetails!!.genres.size){
                Genre(movieViewModel!!.movieDetails!!.genres[i].name)
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
                enabled = !postedReview,
                onClick = { movieViewModel.openDialog() }
            ) {
                Icon(
                    modifier = Modifier.padding(13.dp,13.dp,13.dp,13.dp),
                    tint = DarkRed,
                    painter = painterResource(R.drawable.plus_icon),
                    contentDescription = "Plus icon")
            }
        }

        MyReviewLoad(postedReview, postedReviewNum, movieViewModel!!.movieDetails!!.reviews)

        for (i in 0 until movieViewModel!!.movieDetails!!.reviews.size){
            if (i != postedReviewNum) {
                movieViewModel.parseDate(i)
                ReviewShow(movieViewModel!!.movieDetails!!.reviews[i], movieViewModel.reviewDate, movieViewModel.userId)
            }
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
fun ReviewShow(review: Review, date: String, userId: String) {
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
                    onClick = { },
                    contentPadding = PaddingValues(16.dp, 4.dp),
                    modifier = Modifier
                        //.defaultMinSize(minWidth = 42.dp, minHeight = 28.dp)
                        .padding(0.dp, 6.dp)
                        .wrapContentSize(),
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
            if (review.author?.userId == userId) {
                Row(
                    verticalAlignment = Alignment.Bottom,
                    //horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = date,
                        Modifier
                            //.fillMaxWidth()
                            .padding(8.dp, 0.dp, 8.dp, 0.dp)
                            .align(CenterVertically),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.W400,
                        textAlign = TextAlign.Start,
                        lineHeight = 14.sp,
                        color = TextGray
                    )
                    Spacer(Modifier.weight(1f))
                    IconButton(
                        onClick = { /* movieViewModel.openDialog() */ }
                    ) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            tint = Gray,
                            painter = painterResource(R.drawable.edit_icon),
                            contentDescription = "Plus icon")
                    }
                    IconButton(
                        onClick = { /* movieViewModel.openDialog() */ }
                    ) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            tint = Red,
                            painter = painterResource(R.drawable.delete_icon),
                            contentDescription = "Plus icon")
                    }
                }
            }
            else {
                Text(
                    text = date,
                    Modifier
                        .fillMaxWidth()
                        .padding(8.dp, 4.dp, 8.dp, 8.dp),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W400,
                    textAlign = TextAlign.Start,
                    lineHeight = 14.sp,
                    color = TextGray
                )
            }
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

@Composable
fun reviewDialog(viewModel: MovieViewModel) {

    val openDialog: Boolean by remember { viewModel.openDialog }
    val text: String by remember { viewModel.text }
    val checkedState: Boolean by remember { viewModel.checker }

    if (openDialog) {
        AlertDialog(
            onDismissRequest = { viewModel.closeDialog() },
            modifier = Modifier.wrapContentSize(),
            buttons = {
                Column(modifier = Modifier.padding(16.dp, 16.dp))
                {
                    Text(
                        text = "Оставить отзыв",
                        modifier = Modifier
                            .fillMaxWidth(),
                        color = White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.W700,
                        lineHeight = 32.sp
                    )
                    starRatingBar(viewModel = viewModel)
                    OutlinedTextField(
                        value = text,
                        onValueChange = { viewModel.onTextChange(it) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(0.dp, 16.dp, 0.dp, 16.dp)
                            .height(120.dp),
                        placeholder = { Text("Отзыв") },
                        enabled = true,
                        shape = RoundedCornerShape(8.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            backgroundColor = White,
                            unfocusedBorderColor = White,
                            focusedBorderColor = White,
                            placeholderColor = ReviewGray,
                            cursorColor = Black,
                            textColor = Black
                        ),
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Анонимный отзыв",
                            modifier = Modifier
                                .padding(0.dp, 0.dp, 0.dp, 16.dp)
                                .align(CenterVertically),
                            color = White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.W500,
                            lineHeight = 20.sp
                        )
                        Checkbox(
                            checked = checkedState,
                            onCheckedChange = { viewModel.onCheckerChange(it) },
                            modifier = Modifier
                                .align(CenterVertically)
                                .padding(0.dp, 0.dp, 0.dp, 16.dp),
                            colors = CheckboxDefaults.colors(
                                checkedColor = ReviewDialogGray,
                                uncheckedColor = Gray,
                                checkmarkColor = DarkRed
                            )
                        )
                    }
                    Button(
                        modifier = Modifier
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(4.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = DarkRed,
                            contentColor = White
                        ),
                        onClick = {
                            viewModel.saveReview(viewModel.movieDetails!!.id)
                            viewModel.closeDialog()
                            viewModel.checkReviews()
                        }
                    )
                    {
                        Text(
                            "Сохранить",
                            color = White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.W500,
                            lineHeight = 20.sp
                        )
                    }
                    Button(
                        modifier = Modifier
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(4.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = ReviewDialogGray,
                            contentColor = DarkRed
                        ),
                        onClick = {
                            viewModel.closeDialog()
                            viewModel.newRating(0)
                        }
                    )
                    {
                        Text(
                            "Отмена",
                            color = DarkRed,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.W500,
                            lineHeight = 20.sp
                        )
                    }
                }
            },
            shape = RoundedCornerShape(16.dp),
            backgroundColor = ReviewDialogGray
        )
    }
}

@Composable
fun starRatingBar(
    modifier: Modifier = Modifier,
    stars: Int = 10,
    starsColor: Color = DarkRed,
    unfilledStarsColor: Color = Gray,
    viewModel: MovieViewModel,
) {
    val rating: Int by remember { viewModel.rating }
    val filledStars = rating
    val unfilledStars = stars - rating

    Row(modifier = modifier.fillMaxWidth().padding(0.dp, 16.dp)) {
        var star = 0
        repeat(filledStars) {
            val cur = star
            Icon(
                modifier = Modifier
                    .weight(0.1f)
                    .clickable {
                    viewModel.newRating(cur + 1)
                },
                painter = painterResource(R.drawable.filled_star),
                contentDescription = null,
                tint = starsColor
            )

            star++
        }

        repeat(unfilledStars) {
            val cur = star
            Icon(
                modifier = Modifier
                    .weight(0.1f)
                    .clickable { viewModel.newRating(cur + 1) },
                painter = painterResource(R.drawable.unfilled_star),
                contentDescription = null,
                tint = unfilledStarsColor
            )

            star++
        }
    }
}

@Composable
fun MyReviewLoad(isPosted: Boolean, num: Int, reviews: List<Review>){
    if (isPosted) {
        val date = reviews[num].createDateTime.substringBefore("T").split('-')

        ReviewShow(
            reviews[num],
            date[2] + "." + date[1] + "." + date[0],
            reviews[num].author!!.userId
        )
    }
}