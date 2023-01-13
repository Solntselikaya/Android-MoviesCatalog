@file:OptIn(ExperimentalMaterialApi::class)

package com.example.movies_catalog.movieScreen

import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomStart
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.TopStart
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.ColorUtils
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.movies_catalog.R
import com.example.movies_catalog.network.models.Review
import com.example.movies_catalog.ui.theme.*
import com.google.accompanist.flowlayout.FlowRow
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

//@Preview(showBackground = true)
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MovieScreen() {
    val movieViewModel: MovieViewModel = viewModel()

    val userHasPostedReview: Boolean by remember {movieViewModel.postedReview}
    val postedReviewNum: Int by remember {movieViewModel.postedReviewNum}
    //val isReviewEdited: Boolean by remember {movieViewModel.editedReview}
    val userReview: Review by remember {movieViewModel.userReviewDetails}

    val isInFavorites: Boolean by remember {movieViewModel.isInFavorites}

    ReviewDialog(movieViewModel)

    val state = rememberCollapsingToolbarScaffoldState()
    CollapsingToolbarScaffold(
        modifier = Modifier.fillMaxSize(),
        state = state,
        scrollStrategy = ScrollStrategy.ExitUntilCollapsed,
        toolbar = {
            Image(
                painter = rememberAsyncImagePainter(movieViewModel.movieDetails!!.poster),
                contentDescription = stringResource(R.string.movies_poster_description),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .parallax(0.5f)
                    .fillMaxWidth()
                    .height(250.dp)
                    .drawWithCache {
                        val gradient = Brush.verticalGradient(
                            colors = listOf(Transparent, Black),
                            startY = size.height / 2,
                            endY = size.height + 10
                        )
                        onDrawWithContent {
                            drawContent()
                            drawRect(gradient, blendMode = BlendMode.Darken)
                        }
                    }
                    .graphicsLayer {
                        // change alpha of Image as the toolbar expands
                        alpha = state.toolbarState.progress
                    }
            )

            Row(
                Modifier
                    .pin()
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CompositionLocalProvider(LocalMinimumTouchTargetEnforcement provides false) {
                    val onBackPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
                    IconButton(
                        onClick = { onBackPressedDispatcher?.onBackPressed() },
                        Modifier
                            .padding(16.dp)
                    ) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            painter = painterResource(R.drawable.arrow_back),
                            contentDescription = null,
                            tint = White,
                        )
                    }
                }

                CompositionLocalProvider(LocalMinimumTouchTargetEnforcement provides false) {
                    IconButton(
                        onClick = { movieViewModel.onFavoritesChange(!isInFavorites) },
                        Modifier
                            .padding(16.dp),
                        enabled = state.toolbarState.progress.equals(0.toFloat())
                    ) {
                        Icon(
                            painter =
                            if (isInFavorites)
                                painterResource(R.drawable.filled_heart)
                            else
                                painterResource(R.drawable.unfilled_heart),
                            contentDescription = null,
                            modifier = Modifier
                                .size(24.dp)
                                .graphicsLayer { alpha = 1 - state.toolbarState.progress },
                            tint = DarkRed
                        )
                    }
                }
            }

            val textSize = (24 + (12) * state.toolbarState.progress).sp
            val textLeftPadding = (49 - (33) * state.toolbarState.progress).dp
            val textRightPadding = (48 - (32) * state.toolbarState.progress).dp
            val textBottomPadding = (12 + (4) * state.toolbarState.progress).dp
            Text(
                text = movieViewModel.movieDetails!!.name,
                Modifier
                    .road(
                        whenCollapsed = TopStart,
                        whenExpanded = BottomStart
                    )
                    .padding(textLeftPadding, 9.dp, textRightPadding, textBottomPadding)
                    .defaultMinSize(minHeight = 32.dp),
                textAlign = TextAlign.Left,
                style = MaterialTheme.typography.h4,
                fontSize = textSize,
                color = White,
                overflow = TextOverflow.Ellipsis,
                maxLines = if (state.toolbarState.progress.equals(0.toFloat())) 1 else Int.MAX_VALUE
            )
        }
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
        ) {
            movieViewModel.checkReviews()

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


                if (!userHasPostedReview) {
                    IconButton(
                        modifier = Modifier.requiredSize(24.dp),
                        //enabled = !postedReview,
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
            }

            if (userHasPostedReview) {
                movieViewModel.parseDate(postedReviewNum)
                ReviewShow(
                    userReview,
                    movieViewModel.reviewDate,
                    movieViewModel.userId,
                    {movieViewModel.openDialog()},
                    {movieViewModel.deleteReview()}
                )
            }

            for (i in 0 until movieViewModel.movieDetails!!.reviews.size) {
                if (i != postedReviewNum) {
                    movieViewModel.parseDate(i)
                    ReviewShow(
                        movieViewModel.movieDetails!!.reviews[i],
                        movieViewModel.reviewDate,
                        movieViewModel.userId,
                        {movieViewModel.openDialog()},
                        {movieViewModel.deleteReview()}
                    )
                }
            }
        }
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
                .height(4.dp))
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
                .height(4.dp))
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
                .height(4.dp))
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
                .height(4.dp))
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
                .height(4.dp))
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
                .height(4.dp))
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
                .height(4.dp))
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
        shape = RoundedCornerShape(8.dp)
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
    userId: String,
    openDialog: () -> Unit,
    deleteReview: () -> Unit
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
                    .height(40.dp)
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
                        //.padding(8.dp, 6.dp, 0.dp, 6.dp)
                        .wrapContentSize()
                        .defaultMinSize(minWidth = 42.dp, minHeight = 28.dp),
                    contentPadding = PaddingValues(16.dp, 4.dp),
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

            Row(
                Modifier
                    .padding(8.dp, 4.dp, 8.dp, 8.dp)
                    .defaultMinSize(minHeight = 24.dp),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = date,
                    Modifier
                        //.fillMaxWidth()
                        .align(CenterVertically),
                    style = MaterialTheme.typography.subtitle2,
                    textAlign = TextAlign.Start,
                    color = TextGray
                )

                if (review.author?.userId == userId) {
                    Spacer(Modifier.weight(1f))

                    CompositionLocalProvider(LocalMinimumTouchTargetEnforcement provides false) {
                        IconButton(
                            onClick = { openDialog() }
                        ) {
                            Icon(
                                modifier = Modifier
                                    .padding(0.dp, 0.dp, 4.dp, 0.dp)
                                    .size(24.dp),
                                tint = Color.Unspecified,
                                painter = painterResource(R.drawable.edit_icon),
                                contentDescription = stringResource(R.string.edit_icon_description)
                            )
                        }

                        IconButton(
                            onClick = { deleteReview() }
                        ) {
                            Icon(
                                modifier = Modifier
                                    .padding(4.dp, 0.dp, 0.dp, 0.dp)
                                    .size(24.dp),
                                tint = Color.Unspecified,
                                painter = painterResource(R.drawable.delete_icon),
                                contentDescription = stringResource(R.string.delete_icon_description)
                            )
                        }
                    }
                }
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
            alignment = Center
        )
    }
}

@Composable
fun ReviewDialog(viewModel: MovieViewModel) {
    val openDialog: Boolean by remember { viewModel.openDialog }
    val reviewText: String by remember { viewModel.text }
    val checkboxState: Boolean by remember { viewModel.isAnonymousChecker }

    if(openDialog) {
        AlertDialog(
            onDismissRequest = { viewModel.closeDialog() },
            modifier = Modifier.wrapContentSize(),
            shape = RoundedCornerShape(16.dp),
            backgroundColor = ReviewDialogGray,
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
                        value = reviewText,
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
                        //checkbox курильщика, зато по макету
                        CompositionLocalProvider(LocalMinimumTouchTargetEnforcement provides false) {
                            val tickColor = if (checkboxState) DarkRed else ReviewDialogGray

                            IconButton(
                                onClick = {viewModel.onAnonymousCheckerChange(!checkboxState)},
                                modifier = Modifier
                                    .align(CenterVertically)
                            ) {
                                Box(Modifier.wrapContentSize()){
                                    Icon(
                                        imageVector = ImageVector.vectorResource(R.drawable.checkbox),
                                        tint = Color.Unspecified,
                                        contentDescription = null,
                                        modifier = Modifier
                                            .align(Center)
                                            .size(24.dp)
                                    )
                                    Icon(
                                        imageVector = ImageVector.vectorResource(R.drawable.tick),
                                        tint = tickColor,
                                        contentDescription = null,
                                        modifier = Modifier
                                            .align(Center)
                                            .size(17.dp, 12.dp)
                                    )
                                }
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
                            viewModel.saveOrEditReview()
                            viewModel.closeDialog()
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
                        onClick = { viewModel.closeDialog() }
                    )
                    {
                        Text(
                            stringResource(R.string.cancel),
                            color = DarkRed,
                            style = MaterialTheme.typography.body1
                        )
                    }
                }
            }
        )
    }
}

@Composable
fun StarRatingBar(
    stars: Int = 10,
    starsColor: Color = DarkRed,
    unfilledStarsColor: Color = Gray,
    viewModel: MovieViewModel
) {
    val filledStars: Int by remember { viewModel.rating }
    val unfilledStars = stars - filledStars

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