package com.sam.tintintdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import coil.annotation.ExperimentalCoilApi
import coil.imageLoader
import com.sam.tintintdemo.ui.theme.TinTintDemoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TinTintDemoTheme {
                MyRouter(
                    modifier = Modifier
                        .fillMaxSize(),
                )
            }
        }
    }

    @OptIn(ExperimentalCoilApi::class)
    override fun onDestroy() {
        // 當離開 APP 時，清除圖片緩存
        if (!isChangingConfigurations) {
            imageLoader.diskCache?.clear()
            imageLoader.memoryCache?.clear()
        }
        super.onDestroy()
    }
}
