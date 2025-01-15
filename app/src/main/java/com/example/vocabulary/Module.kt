package com.example.vocabulary

import com.example.vocabulary.network.WordRetriever
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Provides
    @Singleton
    fun provideWordRetriever(): WordRetriever {
        return WordRetriever()
    }
}