package com.sam.tintintdemo

import android.app.Application
import android.content.Context
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.request.CachePolicy
import coil.util.DebugLogger
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.facebook.soloader.SoLoader
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class TinTintApplication : Application(), ImageLoaderFactory {
    private val networkFlipperPlugin = NetworkFlipperPlugin()

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())

            SoLoader.init(this, false)
            if (FlipperUtils.shouldEnableFlipper(this)) {
                val client = AndroidFlipperClient.getInstance(this)
                client.addPlugin(InspectorFlipperPlugin(this, DescriptorMapping.withDefaults()))
                client.addPlugin(networkFlipperPlugin)
                client.start()
            }
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
