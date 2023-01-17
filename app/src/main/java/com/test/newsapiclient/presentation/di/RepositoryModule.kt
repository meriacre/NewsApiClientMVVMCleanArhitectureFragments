package com.test.newsapiclient.presentation.di

import com.test.newsapiclient.data.repository.NewsRepositoryImpl
import com.test.newsapiclient.data.repository.datasource.NewsLocalDataSource
import com.test.newsapiclient.data.repository.datasource.NewsRemoteDataSource
import com.test.newsapiclient.domain.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideNewsRepository(newsRemoteDataSource: NewsRemoteDataSource, newsLocalDataSource: NewsLocalDataSource): NewsRepository{
        return NewsRepositoryImpl(newsRemoteDataSource, newsLocalDataSource)
    }
}