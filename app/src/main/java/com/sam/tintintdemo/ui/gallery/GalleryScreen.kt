package com.sam.tintintdemo.ui.gallery

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.sam.tintintdemo.R
import com.sam.tintintdemo.data.MyGalleryData
import com.sam.tintintdemo.ui.LoadingIndicator

private const val GRID_CELL_COUNT = 4

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GalleryScreen(
    navController: NavController,
    viewModel: GalleryViewModel = hiltViewModel(),
) {
    val snackBarHostState = remember { SnackbarHostState() }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.gallery_title),
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.navigateUp()
                        },
                        content = {
                            Icon(
                                modifier = Modifier
                                    .size(48.dp),
                                imageVector = Icons.AutoMirrored.Default.KeyboardArrowLeft,
                                tint = Color.Black,
                                contentDescription = "back",
                            )
                        }
                    )
                }
            )
        },
        snackbarHost = {
            SnackbarHost(snackBarHostState) { data ->
                Snackbar(
                    containerColor = Color.Red,
                    snackbarData = data,
                )
            }
        }
    ) { contentPadding ->
        val lazyGalleryItems = viewModel.galleryPagingFlow.collectAsLazyPagingItems()

        LaunchedEffect(lazyGalleryItems.loadState) {
            (lazyGalleryItems.loadState.refresh as? LoadState.Error)?.error?.localizedMessage?.let {
                snackBarHostState.showSnackbar(it)
                return@LaunchedEffect
            }
            (lazyGalleryItems.loadState.append as? LoadState.Error)?.error?.localizedMessage?.let {
                snackBarHostState.showSnackbar(it)
            }
        }

        GalleryContent(
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxSize(),
            lazyGalleryItems = lazyGalleryItems,
        )

        if (lazyGalleryItems.loadState.refresh is LoadState.Loading) {
            LoadingIndicator(
                modifier = Modifier
                    .fillMaxSize(),
            )
        }
    }
}

@Composable
fun GalleryContent(
    modifier: Modifier = Modifier,
    lazyGalleryItems: LazyPagingItems<MyGalleryData>,
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(GRID_CELL_COUNT),
    ) {
        items(count = lazyGalleryItems.itemCount) { index ->
            val galleryData = lazyGalleryItems[index] ?: return@items

            GalleryItemView(
                modifier = Modifier
                    .fillMaxWidth(),
                data = galleryData,
            )
        }

        if (lazyGalleryItems.loadState.append is LoadState.Loading) {
            item(span = { GridItemSpan(this.maxLineSpan) }) {
                LoadingIndicator(
                    modifier = Modifier.fillMaxSize(),
                    padding = PaddingValues(vertical = 16.dp),
                    size = 24.dp,
                )
            }
        }
    }
}

@Composable
fun GalleryItemView(
    modifier: Modifier = Modifier,
    data: MyGalleryData,
) {
    BoxWithConstraints(
        modifier = modifier
            .aspectRatio(1f),
    ) {
        val widthPx = with(LocalDensity.current) { maxWidth.toPx().toInt() }
        val model = ImageRequest.Builder(LocalContext.current)
            .data(data.thumbnailUrl)
            .size(widthPx, widthPx)
            .build()

        SubcomposeAsyncImage(
            modifier = Modifier
                .fillMaxSize(),
            model = model,
            loading = {
                LoadingIndicator(
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha(0.2f),
                    size = 16.dp,
                )
            },
            contentDescription = data.title,
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = data.id.toString(),
            )

            Spacer(
                modifier = Modifier.size(4.dp),
            )

            Text(
                text = data.title,
                maxLines = 2,
                minLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}
