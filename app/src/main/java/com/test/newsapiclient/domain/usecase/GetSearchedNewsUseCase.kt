package com.test.newsapiclient.domain.usecase

import com.test.newsapiclient.data.model.ApiResponse
import com.test.newsapiclient.data.util.Resource
import com.test.newsapiclient.domain.repository.NewsRepository

class GetSearchedNewsUseCase(private val newsRepository: NewsRepository) {

    suspend fun execute(country:String, searchQuery:String, page: Int): Resource<ApiResponse> {
        return newsRepository.getSearchedNews(country, searchQuery, page)
    }
}