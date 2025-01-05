package com.sam.tintintdemo.ui.gallery

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.sam.tintintdemo.R
import com.sam.tintintdemo.data.GalleryData
import com.sam.tintintdemo.ui.LoadingIndicator

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
        val errorMsg by viewModel.errorMessage.collectAsState("")
        val isLoading by viewModel.isLoading.collectAsState(false)
        val lazyGalleryItems = viewModel.galleryPagingFlow.collectAsLazyPagingItems()

        LaunchedEffect(errorMsg) {
            if (errorMsg.isNotEmpty()) {
                snackBarHostState.showSnackbar(errorMsg)
            }
        }

        GalleryContent(
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxSize(),
            lazyGalleryItems = lazyGalleryItems,
        )

        if (isLoading) LoadingIndicator(
            modifier = Modifier
                .fillMaxSize(),
        )
    }
}

@Composable
fun GalleryContent(
    modifier: Modifier = Modifier,
    lazyGalleryItems: LazyPagingItems<GalleryData>,
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(
            count = lazyGalleryItems.itemCount,
        ) { index ->
            val galleryData = lazyGalleryItems[index] ?: return@items

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                text = galleryData.title,
            )
        }
    }
}
