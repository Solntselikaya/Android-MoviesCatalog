package com.example.movies_catalog.movieScreen

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.graphics.ColorUtils
import coil.compose.rememberAsyncImagePainter
import com.example.movies_catalog.R
import com.example.movies_catalog.network.models.Review
import com.example.movies_catalog.ui.theme.*
import com.google.accompanist.flowlayout.FlowRow

//@Preview(showBackground = true)
@Composable
fun MovieScreen() {

    val movieViewModel = MovieViewModel()

    ReviewDialog(viewModel = movieViewModel)

    val postedReview: Boolean by remember { movieViewModel.postedReview }
    val postedReviewNum: Int by remember { movieViewModel.postedReviewNum }

    //movieViewModel.checkReviews()

    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        movieViewModel.checkReviews()
        Poster(movieViewModel.movieDetails!!.poster, movieViewModel.movieDetails!!.name)
        Text(
            text = movieViewModel.movieDetails!!.description,
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
            style = MaterialTheme.typography.body2,
            textAlign = TextAlign.Start,
            color = White
        )
        Text(
            text = stringResource(R.string.about_movie),
            Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp),
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Start,
            color = White
        )
        Description(movieViewModel)
        Text(
            text = stringResource(R.string.genres),
            Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp),
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Start,
            color = White
        )
        FlowRow(
            Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp, 8.dp)
        ) {
            for (i in 0 until movieViewModel.movieDetails!!.genres.size) {
                Genre(movieViewModel.movieDetails!!.genres[i].name)
            }
        }
        Row(
            Modifier
                .padding(16.dp, 8.dp)
                .fillMaxWidth()
                .height(24.dp),
            verticalAlignment = CenterVertically
        ) {
            Text(
                text = stringResource(R.string.reviews),
                modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 4.dp),
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Start,
                color = White
            )
            Spacer(Modifier.weight(1f))
            IconButton(
                modifier = Modifier.requiredSize(24.dp),
                enabled = !postedReview,
                onClick = { movieViewModel.openDialog() }
            ) {
                Icon(
                    modifier = Modifier
                        .padding(4.dp)
                        .size(16.dp),
                    tint = DarkRed,
                    painter = painterResource(R.drawable.plus_icon),
                    contentDescription = stringResource(R.string.plus_icon_description)
                )
            }
        }

        MyReviewLoad(postedReview, postedReviewNum, movieViewModel.movieDetails!!.reviews)

        for (i in 0 until movieViewModel.movieDetails!!.reviews.size) {
            if (i != postedReviewNum) {
                movieViewModel.parseDate(i)
                ReviewShow(
                    movieViewModel.movieDetails!!.reviews[i],
                    movieViewModel.reviewDate,
                    movieViewModel.userId
                )
            }
        }
    }
}

@Composable
fun Poster(image: String, name: String) {
    Box(modifier = Modifier.wrapContentSize()) {
        Image(
            painter = rememberAsyncImagePainter(image),
            contentDescription = stringResource(R.string.movies_poster_description),
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
            style = MaterialTheme.typography.h4,
            color = White
        )
    }
}

@Composable
fun Description(viewModel: MovieViewModel) {
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
                text = stringResource(R.string.year),
                Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 0.dp, 8.dp, 0.dp)
                    .weight(0.34f),
                style = MaterialTheme.typography.subtitle1,
                textAlign = TextAlign.Start,
                color = TextGray
            )
            Text(
                text = viewModel.movieDetails!!.year.toString(),
                Modifier
                    .fillMaxWidth()
                    .weight(0.66f),
                style = MaterialTheme.typography.subtitle1,
                textAlign = TextAlign.Start,
                color = White
            )
        }
        Spacer(
            Modifier
                .fillMaxWidth()
                .height(4.dp)
        )
        Row(
            Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Text(
                text = stringResource(R.string.country),
                Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 0.dp, 8.dp, 0.dp)
                    .weight(0.34f),
                style = MaterialTheme.typography.subtitle1,
                textAlign = TextAlign.Start,
                color = TextGray
            )
            Text(
                text = viewModel.movieDetails!!.country,
                Modifier
                    .fillMaxWidth()
                    .weight(0.66f),
                style = MaterialTheme.typography.subtitle1,
                textAlign = TextAlign.Start,
                color = White
            )
        }
        Spacer(
            Modifier
                .fillMaxWidth()
                .height(4.dp)
        )
        Row(
            Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Text(
                text = stringResource(R.string.time),
                Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 0.dp, 8.dp, 0.dp)
                    .weight(0.34f),
                style = MaterialTheme.typography.subtitle1,
                textAlign = TextAlign.Start,
                color = TextGray
            )
            Text(
                text = viewModel.movieDetails!!.time.toString() + stringResource(R.string.minutes),
                Modifier
                    .fillMaxWidth()
                    .weight(0.66f),
                style = MaterialTheme.typography.subtitle1,
                textAlign = TextAlign.Start,
                color = White
            )

        }
        Spacer(
            Modifier
                .fillMaxWidth()
                .height(4.dp)
        )
        Row(
            Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Text(
                text = stringResource(R.string.slogan),
                Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 0.dp, 8.dp, 0.dp)
                    .weight(0.34f),
                style = MaterialTheme.typography.subtitle1,
                textAlign = TextAlign.Start,
                color = TextGray
            )
            Text(
                text = "\"" + viewModel.movieDetails!!.tagline + "\"",
                Modifier
                    .fillMaxWidth()
                    .weight(0.66f),
                style = MaterialTheme.typography.subtitle1,
                textAlign = TextAlign.Start,
                color = White
            )
        }
        Spacer(
            Modifier
                .fillMaxWidth()
                .height(4.dp)
        )
        Row(
            Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Text(
                text = stringResource(R.string.director),
                Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 0.dp, 8.dp, 0.dp)
                    .weight(0.34f),
                style = MaterialTheme.typography.subtitle1,
                textAlign = TextAlign.Start,
                color = TextGray
            )
            Text(
                text = viewModel.movieDetails!!.director,
                Modifier
                    .fillMaxWidth()
                    .weight(0.66f),
                style = MaterialTheme.typography.subtitle1,
                textAlign = TextAlign.Start,
                color = White
            )
        }
        Spacer(
            Modifier
                .fillMaxWidth()
                .height(4.dp)
        )
        Row(
            Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Text(
                text = stringResource(R.string.budget),
                Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 0.dp, 8.dp, 0.dp)
                    .weight(0.34f),
                style = MaterialTheme.typography.subtitle1,
                textAlign = TextAlign.Start,
                color = TextGray
            )
            Text(
                text = "\$" + viewModel.showBudget,
                Modifier
                    .fillMaxWidth()
                    .weight(0.66f),
                style = MaterialTheme.typography.subtitle1,
                textAlign = TextAlign.Start,
                color = White
            )
        }
        Spacer(
            Modifier
                .fillMaxWidth()
                .height(4.dp)
        )
        Row(
            Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Text(
                text = stringResource(R.string.fees),
                Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 0.dp, 8.dp, 0.dp)
                    .weight(0.34f),
                style = MaterialTheme.typography.subtitle1,
                textAlign = TextAlign.Start,
                color = TextGray
            )
            Text(
                text = "\$" + viewModel.showFees,
                Modifier
                    .fillMaxWidth()
                    .weight(0.66f),
                style = MaterialTheme.typography.subtitle1,
                textAlign = TextAlign.Start,
                color = White
            )
        }
        Spacer(
            Modifier
                .fillMaxWidth()
                .height(4.dp)
        )
        Row(
            Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Text(
                text = stringResource(R.string.age_limit),
                Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 0.dp, 8.dp, 0.dp)
                    .weight(0.34f),
                style = MaterialTheme.typography.subtitle1,
                textAlign = TextAlign.Start,
                color = TextGray
            )
            Text(
                text = viewModel.movieDetails!!.ageLimit.toString() + "+",
                Modifier
                    .fillMaxWidth()
                    .weight(0.66f),
                style = MaterialTheme.typography.subtitle1,
                textAlign = TextAlign.Start,
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
        shape = RoundedCornerShape(16.dp)
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.subtitle1,
            textAlign = TextAlign.Center,
            color = White
        )
    }
}

@Composable
fun ReviewShow(
    review: Review,
    date: String,
    userId: String
) {
    Card(
        Modifier.padding(16.dp, 4.dp),
        shape = RoundedCornerShape(8.dp),
        backgroundColor = Black,
        border = BorderStroke(1.dp, GrayFaded),
    ) {
        Column(Modifier.fillMaxSize()) {
            Row(
                verticalAlignment = CenterVertically,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            )
            {
                Avatar(review.author?.avatar, review.isAnonymous)
                Column(
                    Modifier.padding(8.dp, 0.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = if (review.isAnonymous) stringResource(R.string.anonymous_user) else review.author!!.nickName,
                        color = White,
                        style = MaterialTheme.typography.body1,
                        textAlign = TextAlign.Left
                    )
                    if (review.author?.userId == userId) {
                        Text(
                            text = stringResource(R.string.my_review),
                            color = TextGray,
                            style = MaterialTheme.typography.subtitle2,
                            textAlign = TextAlign.Left
                        )
                    }
                }
                Spacer(Modifier.weight(1f))
                val color = ColorUtils.blendARGB(Red.toArgb(), Green.toArgb(), review.rating * 0.1f)
                Button(
                    onClick = { },
                    modifier = Modifier
                        .padding(8.dp, 6.dp, 0.dp, 6.dp)
                        .height(28.dp)
                        .width(42.dp),
                    contentPadding = PaddingValues(0.dp),
                    enabled = false,
                    colors = ButtonDefaults.buttonColors(
                        disabledBackgroundColor = Color(color),
                        disabledContentColor = White
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = review.rating.toString(),
                        style = MaterialTheme.typography.body1,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.align(CenterVertically),
                        color = White
                    )
                }
            }
            Text(
                text = review.reviewText,
                Modifier
                    .padding(8.dp, 0.dp)
                    .fillMaxWidth(),
                style = MaterialTheme.typography.body2,
                textAlign = TextAlign.Start,
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
                        style = MaterialTheme.typography.subtitle2,
                        textAlign = TextAlign.Start,
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
                            contentDescription = stringResource(R.string.edit_icon_description)
                        )
                    }
                    IconButton(
                        onClick = { /* movieViewModel.openDialog() */ }
                    ) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            tint = Red,
                            painter = painterResource(R.drawable.delete_icon),
                            contentDescription = stringResource(R.string.delete_icon_description)
                        )
                    }
                }
            } else {
                Text(
                    text = date,
                    Modifier
                        .fillMaxWidth()
                        .padding(8.dp, 4.dp, 8.dp, 8.dp),
                    style = MaterialTheme.typography.subtitle2,
                    textAlign = TextAlign.Start,
                    color = TextGray
                )
            }
        }
    }
}

@Composable
fun Avatar(image: String?, isAnonymous: Boolean) {

    val avatar: String =
        if (image == "" || isAnonymous || image == null)
            stringResource(R.string.default_avatar)
        else
            image

    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(White)
    ) {
        Image(
            painter = rememberAsyncImagePainter(avatar),
            contentDescription = stringResource(R.string.avatar_content_description),
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center
        )
    }
}

@Composable
fun ReviewDialog(viewModel: MovieViewModel) {

    val openDialog: Boolean by remember { viewModel.openDialog }
    val text: String by remember { viewModel.text }
    val checkedState: Boolean by remember { viewModel.checker }

    if (openDialog) {
        AlertDialog(
            onDismissRequest = { viewModel.closeDialog() },
            modifier = Modifier.wrapContentSize(),
            buttons = {
                Column(modifier = Modifier.padding(16.dp))
                {
                    Text(
                        text = stringResource(R.string.leave_a_review),
                        modifier = Modifier
                            .fillMaxWidth(),
                        color = White,
                        style = MaterialTheme.typography.h5
                    )
                    StarRatingBar(viewModel = viewModel)
                    OutlinedTextField(
                        value = text,
                        onValueChange = { viewModel.onTextChange(it) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp),
                        textStyle = MaterialTheme.typography.caption,
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
                        placeholder = {
                            Text(
                                stringResource(R.string.review),
                                color = ReviewGray,
                                style = MaterialTheme.typography.caption
                            )
                        },
                    )
                    Row(
                        modifier = Modifier
                            .padding(0.dp, 16.dp)
                            .fillMaxWidth()
                            .height(24.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = stringResource(R.string.anonymous_review),
                            modifier = Modifier
                                .align(CenterVertically),
                            color = White,
                            style = MaterialTheme.typography.body1
                        )

                        /* normal checkbox
                        Checkbox(
                            checked = checkedState,
                            onCheckedChange = { viewModel.onCheckerChange(it) },
                            modifier = Modifier
                                .align(CenterVertically),
                            colors = CheckboxDefaults.colors(
                                checkedColor = ReviewDialogGray,
                                uncheckedColor = CheckerGray,
                                checkmarkColor = DarkRed
                            )
                        )
                        */

                        //Spacer(Modifier.weight(1f))

                        //checkbox курильщика, зато по макету
                        val tickColor =
                            if (checkedState) DarkRed else ReviewDialogGray

                        IconButton(
                            onClick = { viewModel.onCheckerChange(!checkedState) },
                            modifier = Modifier
                                .align(CenterVertically)
                        ) {
                            Box(Modifier.wrapContentSize()){
                                Icon(
                                    imageVector = ImageVector.vectorResource(R.drawable.checkbox),
                                    tint = CheckerGray,
                                    contentDescription = "checkbox",
                                    modifier = Modifier.align(Center)
                                )
                                Icon(
                                    imageVector = ImageVector.vectorResource(R.drawable.tick),
                                    tint = tickColor,
                                    contentDescription = "tick",
                                    modifier = Modifier.align(Center)
                                )
                            }
                        }
                    }
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp),
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
                            stringResource(R.string.save),
                            color = White,
                            style = MaterialTheme.typography.body1
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
                            stringResource(R.string.cancel),
                            color = DarkRed,
                            style = MaterialTheme.typography.body1
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
fun StarRatingBar(
    stars: Int = 10,
    starsColor: Color = DarkRed,
    unfilledStarsColor: Color = Gray,
    viewModel: MovieViewModel,
) {
    val rating: Int by remember { viewModel.rating }
    val filledStars = rating
    val unfilledStars = stars - rating

    Row(
        Modifier
            .padding(0.dp, 16.dp)
            .fillMaxWidth()
    ) {
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
fun MyReviewLoad(isPosted: Boolean, num: Int, reviews: List<Review>) {
    if (isPosted) {
        val date = reviews[num].createDateTime.substringBefore("T").split('-')

        ReviewShow(
            reviews[num],
            date[2] + "." + date[1] + "." + date[0],
            reviews[num].author!!.userId
        )
    }
}

/*
@Preview(showBackground = true)
@Composable
fun ReviewSh() {
    Card(
        Modifier.padding(16.dp, 4.dp),
        shape = RoundedCornerShape(8.dp),
        backgroundColor = Black,
        border = BorderStroke(1.dp, GrayFaded),
    ) {
        Column() {
            Row(
                verticalAlignment = CenterVertically,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            )
            {
                Avatar("", true)
                Column(
                    Modifier
                        .padding(8.dp, 0.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "bdfyjd bdfy bdfhhhkhkhkhkh",
                        color = White,
                        style = MaterialTheme.typography.body1,
                        textAlign = TextAlign.Left
                    )
                    Text(
                        text = stringResource(R.string.my_review),
                        color = TextGray,
                        style = MaterialTheme.typography.subtitle2,
                        textAlign = TextAlign.Left
                    )
                    /*
                    if (review.author?.userId == userId) {
                        Text(
                            text = stringResource(R.string.my_review),
                            color = TextGray,
                            style = MaterialTheme.typography.subtitle2,
                            textAlign = TextAlign.Left
                        )
                    }
                     */
                }
                Spacer(Modifier.weight(1f))
                val color = ColorUtils.blendARGB(Red.toArgb(), Green.toArgb(), 8 * 0.1f)
                Button(
                    onClick = { },
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier
                        .padding(8.dp, 6.dp, 0.dp, 6.dp)
                        .height(28.dp)
                        .width(42.dp),
                    enabled = false,
                    colors = ButtonDefaults.buttonColors(
                        disabledBackgroundColor = Color(color),
                        disabledContentColor = White
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = "9.1",
                        style = MaterialTheme.typography.body1,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.align(CenterVertically),
                        color = White
                    )
                }
            }
            Text(
                text = "блалаальавалалвьаывазшвыоащзвыоавоазоывщаощывоашщзвоаышщаышщовщаошщвоышщаоышщоаыоазыоваоышоашщвыоашыоашвыоашщо",
                Modifier
                    .padding(8.dp, 0.dp)
                    .fillMaxWidth(),
                style = MaterialTheme.typography.body2,
                textAlign = TextAlign.Start,
                color = White
            )
            /*
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
                        style = MaterialTheme.typography.subtitle2,
                        textAlign = TextAlign.Start,
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
                            contentDescription = stringResource(R.string.edit_icon_description)
                        )
                    }
                    IconButton(
                        onClick = { /* movieViewModel.openDialog() */ }
                    ) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            tint = Red,
                            painter = painterResource(R.drawable.delete_icon),
                            contentDescription = stringResource(R.string.delete_icon_description)
                        )
                    }
                }
            } else {
                Text(
                    text = date,
                    Modifier
                        .fillMaxWidth()
                        .padding(8.dp, 4.dp, 8.dp, 8.dp),
                    style = MaterialTheme.typography.subtitle2,
                    textAlign = TextAlign.Start,
                    color = TextGray
                )
            }
             */
            Text(
                text = "22.05.2011",
                Modifier
                    .fillMaxWidth()
                    .padding(8.dp, 4.dp, 8.dp, 8.dp),
                style = MaterialTheme.typography.subtitle2,
                textAlign = TextAlign.Start,
                color = TextGray
            )
        }
    }
}
*/
