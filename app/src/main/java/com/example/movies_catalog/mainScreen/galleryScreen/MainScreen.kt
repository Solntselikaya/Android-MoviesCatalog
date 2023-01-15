@file:OptIn(ExperimentalMaterialApi::class)

package com.example.movies_catalog.mainScreen.galleryScreen

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomStart
import androidx.compose.ui.Alignment.Companion.TopEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.Color.Companion.Unspecified
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.graphics.ColorUtils
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.movies_catalog.R
import com.example.movies_catalog.network.models.MovieElement
import com.example.movies_catalog.network.models.ReviewShort
import com.example.movies_catalog.ui.theme.*

@Composable
fun MainScreen(openMovieDescription: () -> Unit) {

    val mainViewModel: MainViewModel = viewModel()

    mainViewModel.updateFavorites()

    //val moviesListSize : Int by remember { mainViewModel.moviesListSize }
    val favList: List<MovieElement> by remember { mainViewModel.favList }
    val isPageReady: Boolean by remember { mainViewModel.isReady }
    //val favListSize: Int by remember { mainViewModel.favListSize }

    if (isPageReady) {
        LazyColumn(
            Modifier
                .fillMaxSize()
                .background(Black)
        ) {
            item {
                FirstMovieCard(
                    mainViewModel
                ) { openMovieDescription() }
            }
            item {
                if (mainViewModel.favListSize.value != 0) {
                    FavoritesList(
                        favList,
                    ) { mainViewModel.deleteFavorite(it)}
                }
            }
            item {
                Text(
                    text = stringResource(R.string.gallery),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 16.dp, 0.dp, 0.dp),
                    color = DarkRed,
                    style = MaterialTheme.typography.h5
                )
            }
            itemsIndexed(
                items = mainViewModel.movieList
            ) { index, item ->

                val lastIndex = mainViewModel.movieList.lastIndex
                val currentIndex = mainViewModel.movieList.indexOf(item)

                if (currentIndex != 0) {
                    mainViewModel.getGenresString(item.genres)
                    val genres = mainViewModel.genresString
                    MovieCard(
                        viewModel = mainViewModel,
                        id = item.id,
                        title = item.name,
                        year = item.year,
                        country = item.country,
                        image = item.poster,
                        genres = genres,
                        reviews = item.reviews
                    ) { openMovieDescription() }
                }

                if (index == lastIndex && mainViewModel.page < 6) {
                    mainViewModel.getMovies()
                }
            }
        }
    }
}

@Composable
fun FirstMovieCard(
    viewModel: MainViewModel,
    openMovieDescription: () -> Unit
) {
    Box(modifier = Modifier.wrapContentSize()) {
        Image(
            painter = rememberAsyncImagePainter(viewModel.movies!!.movies[0].poster),
            contentDescription = "Movie's Poster",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(320.dp)
                .drawWithCache {
                    val gradient = Brush.verticalGradient(
                        colors = listOf(Unspecified, Black),
                        startY = size.height / 2,
                        endY = size.height
                    )
                    onDrawWithContent {
                        drawContent()
                        drawRect(gradient, blendMode = BlendMode.Darken)
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
            shape = RoundedCornerShape(4.dp)
        )
        {
            Text(
                stringResource(R.string.watch),
                style = MaterialTheme.typography.body1
            )
        }
    }
}

@Composable
fun FavoriteMovieCard(
    image: String,
    id: String,
    isNeedAnimation: Boolean,
    deleteFromFavorite: (String) -> Unit
) {

    var isNeedAnim by remember { mutableStateOf(false) }

    if (isNeedAnimation) {
        LaunchedEffect(isNeedAnim) {
            isNeedAnim = true
        }
    }

    val animatedHeightDp: Dp by animateDpAsState(
        targetValue = if (isNeedAnim) 172.dp else 144.dp,
        animationSpec = tween(durationMillis = 400)
    )
    val animatedWidthDp: Dp by animateDpAsState(
        targetValue = if (isNeedAnim) 120.dp else 100.dp,
        animationSpec = tween(durationMillis = 400)
    )

    Box(
        modifier = Modifier
            .size(animatedWidthDp, animatedHeightDp)
            .clip(RoundedCornerShape(8.dp))

    ) {
        Image(
            painter = rememberAsyncImagePainter(image),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds,
            alignment = Alignment.Center
        )

        CompositionLocalProvider(LocalMinimumTouchTargetEnforcement provides false) {
            IconButton(
                onClick = { deleteFromFavorite(id) },
                Modifier
                    .padding(4.dp)
                    .align(TopEnd)
            ) {
                Icon(
                    painter = painterResource(R.drawable.delete_from_favorites_button),
                    contentDescription = null,
                    Modifier.size(12.dp),
                    tint = Unspecified
                )
            }
        }
    }
}

@Composable
fun FavoritesList(
    items: List<MovieElement>,
    deleteFromFavorite: (String) -> Unit
) {
    Text(
        text = stringResource(R.string.favourites),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 32.dp, 0.dp, 0.dp),
        color = DarkRed,
        style = MaterialTheme.typography.h5
    )

    val rowState = rememberLazyListState()
    val fullyVisibleIndices: List<Int> by remember {
        derivedStateOf {
            val layoutInfo = rowState.layoutInfo
            val visibleItemsInfo = layoutInfo.visibleItemsInfo
            val fullyVisibleItemsInfo = visibleItemsInfo.toMutableList()

            if (visibleItemsInfo.isEmpty()) {
                emptyList()
            }
            else {
                val firstItemIfLeft = fullyVisibleItemsInfo.firstOrNull()
                if (firstItemIfLeft != null && firstItemIfLeft.offset < layoutInfo.viewportStartOffset) {
                    fullyVisibleItemsInfo.removeFirst()
                }

                fullyVisibleItemsInfo.map { it.index }
            }
        }
    }

    LazyRow(
        state = rowState,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 8.dp, 0.dp, 16.dp)
            .height(172.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        //flingBehavior = rememberSnapperFlingBehavior(
        //    lazyListState = rowState,
        //    snapOffsetForItem = SnapOffsets.Start
        //),
        //flingBehavior = rememberSnapFlingBehavior(lazyListState = rowState)
    ) {
        itemsIndexed (items) { index, item ->

            if (fullyVisibleIndices.isNotEmpty() && index == fullyVisibleIndices.first()) {
                //updateAnimation(true)
                FavoriteMovieCard(
                    item.poster,
                    item.id,
                    true,
                ) { deleteFromFavorite(it) }
            }
            else {
                //updateAnimation(false)
                FavoriteMovieCard(
                    item.poster,
                    item.id,
                    false,
                ) { deleteFromFavorite(it) }
            }
        }
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
    reviews: List<ReviewShort>,
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
                .wrapContentHeight()
        ) {
            Image(
                painter = rememberAsyncImagePainter(image),
                contentDescription = null,
                modifier = Modifier
                    .height(144.dp)
                    .weight(0.3f)
            )
            Column(
                modifier = Modifier
                    .padding(16.dp, 0.dp, 0.dp, 0.dp)
                    .wrapContentHeight()
                    .weight(0.7f),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = title,
                    color = White,
                    style = MaterialTheme.typography.h3
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "$year • $country",
                    color = White,
                    style = MaterialTheme.typography.body2
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = genres,
                    color = White,
                    style = MaterialTheme.typography.body2,
                    overflow = TextOverflow.Visible
                )
                Spacer(modifier = Modifier.height(4.dp))
                Box(Modifier.fillMaxSize()) {
                    viewModel.getMovieRating(reviews)
                    //выносить
                    val color =
                        ColorUtils.blendARGB(Red.toArgb(), Green.toArgb(), viewModel.rating * 0.1f)
                    Button(
                        { },
                        contentPadding = PaddingValues(16.dp, 4.dp),
                        modifier = Modifier
                            .wrapContentSize()
                            .defaultMinSize(minWidth = 56.dp, minHeight = 28.dp)
                            .align(BottomStart),
                        enabled = false,
                        colors = ButtonDefaults.buttonColors(
                            disabledBackgroundColor = Color(color),
                            disabledContentColor = White
                        ),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text(
                            text = String.format("%.1f", viewModel.rating),
                            style = MaterialTheme.typography.body1,
                            textAlign = TextAlign.Center,
                            color = White
                        )
                    }
                }
            }
        }
    }
}