package com.test.newsapiclient.domain.usecase

import com.test.newsapiclient.data.model.ApiResponse
import com.test.newsapiclient.data.util.Resource
import com.test.newsapiclient.domain.repository.NewsRepository

class GetNewsHeadlinesUseCase(private val newsRepository: NewsRepository) {

    suspend fun execute(country:String, page: Int): Resource<ApiResponse> = newsRepository.getNewsHeadlines(country, page)

}