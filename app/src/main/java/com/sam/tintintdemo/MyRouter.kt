package com.sam.tintintdemo

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sam.tintintdemo.ui.gallery.GalleryScreen
import com.sam.tintintdemo.ui.home.HomeScreen
import kotlinx.serialization.Serializable

@Serializable
data object Home

@Serializable
data object Gallery

@Composable
fun MyRouter(
    modifier: Modifier = Modifier,
) {
    val navController = rememberNavController()

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Home,
    ) {
        composable<Home> {
            HomeScreen(
                navController = navController,
            )
        }
        composable<Gallery> {
            GalleryScreen(
                navController = navController,
            )
        }
    }
}
