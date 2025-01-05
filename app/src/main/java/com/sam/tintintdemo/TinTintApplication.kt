package com.sam.tintintdemo

import android.app.Application
import android.content.Context
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.annotation.ExperimentalCoilApi
import coil.disk.DiskCache
import coil.imageLoader
import coil.memory.MemoryCache
import coil.request.CachePolicy
import coil.util.DebugLogger
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class TinTintApplication : Application(), ImageLoaderFactory {
    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    /**
     * 運用 Coil 的緩存機制設定緩存限制
     */
    override fun newImageLoader(): ImageLoader {
        return ImageLoader(this).newBuilder()
            .memoryCachePolicy(CachePolicy.ENABLED)
            .memoryCache {
                MemoryCache.Builder(this)
                    .maxSizePercent(0.1)
                    .weakReferencesEnabled(true)
                    .build()
            }
            .diskCachePolicy(CachePolicy.ENABLED)
            .diskCache {
                DiskCache.Builder()
                    .maxSizePercent(0.1)
                    .directory(cacheDir.resolve(IMAGE_CACHE_DIR))
                    .build()
            }
            .logger(DebugLogger())
            .build()
    }

    companion object {
        lateinit var appContext: Context
        private const val IMAGE_CACHE_DIR = "image_cache"
    }
}
