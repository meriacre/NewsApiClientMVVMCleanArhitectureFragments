package com.test.newsapiclient.presentation.di

import com.test.newsapiclient.domain.repository.NewsRepository
import com.test.newsapiclient.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Singleton
    @Provides
    fun provideNewsHeadLinesUseCase(newsRepository: NewsRepository): GetNewsHeadlinesUseCase{
        return GetNewsHeadlinesUseCase(newsRepository)
    }

    @Singleton
    @Provides
    fun provideSearchedNewsUseCase(newsRepository: NewsRepository): GetSearchedNewsUseCase{
        return GetSearchedNewsUseCase(newsRepository)
    }

    @Singleton
    @Provides
    fun provideSaveNewsUseCase(newsRepository: NewsRepository): SaveNewsUseCase{
        return SaveNewsUseCase(newsRepository)
    }

    @Singleton
    @Provides
    fun provideGetSaveNewsUseCase(newsRepository: NewsRepository): GetSavedNewsUseCase{
        return GetSavedNewsUseCase(newsRepository)
    }

    @Singleton
    @Provides
    fun provideDeleteSavedNewsUseCase(newsRepository: NewsRepository): DeleteSavedNewsUseCase{
        return DeleteSavedNewsUseCase(newsRepository)
    }

    @Singleton
    @Provides
    fun provideAllNewsUseCase(newsRepository: NewsRepository): GetAllNewsUseCase{
        return GetAllNewsUseCase(newsRepository)
    }

}