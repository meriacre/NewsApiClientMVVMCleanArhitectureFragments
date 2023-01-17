package com.test.newsapiclient.domain.usecase

import com.test.newsapiclient.data.model.ApiResponse
import com.test.newsapiclient.data.model.Article
import com.test.newsapiclient.data.util.Resource
import com.test.newsapiclient.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class GetSavedNewsUseCase(private val newsRepository: NewsRepository) {

    fun execute(): Flow<List<Article>> = newsRepository.getSavedNews()
}