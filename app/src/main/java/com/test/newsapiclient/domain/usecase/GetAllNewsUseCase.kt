package com.test.newsapiclient.domain.usecase

import com.test.newsapiclient.data.model.ApiResponse
import com.test.newsapiclient.data.util.Resource
import com.test.newsapiclient.domain.repository.NewsRepository

class GetAllNewsUseCase(private val newsRepository: NewsRepository) {
    suspend fun execute(language: String, page: Int): Resource<ApiResponse> = newsRepository.getAllNews(language, page)
}