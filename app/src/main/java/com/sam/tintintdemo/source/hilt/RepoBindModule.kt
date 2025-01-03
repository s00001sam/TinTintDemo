package com.sam.tintintdemo.source.hilt

import com.sam.tintintdemo.source.repo.BaseRepository
import com.sam.tintintdemo.source.repo.TinTintRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepoBindModule {
    @Binds
    @Singleton
    abstract fun bindTinTintRepository(impl: TinTintRepository): BaseRepository
}
