package com.test.newsapiclient.presentation.di

import android.app.Application
import com.test.newsapiclient.domain.usecase.*
import com.test.newsapiclient.presentation.viewmodel.NewsViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FactoryModule {

    @Singleton
    @Provides
    fun providesViewModelFactory(
        application: Application,
        getNewsHeadlinesUseCase: GetNewsHeadlinesUseCase,
        getSearchedNewsUseCase: GetSearchedNewsUseCase,
        saveNewsUseCase: SaveNewsUseCase,
        getSavedNewsUseCase: GetSavedNewsUseCase,
        deleteSavedNewsUseCase: DeleteSavedNewsUseCase,
        getAllNewsUseCase: GetAllNewsUseCase
    ): NewsViewModelFactory{
        return NewsViewModelFactory(application, getNewsHeadlinesUseCase, getSearchedNewsUseCase, saveNewsUseCase, getSavedNewsUseCase, deleteSavedNewsUseCase, getAllNewsUseCase)
    }
}