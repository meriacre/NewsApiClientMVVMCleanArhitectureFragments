package com.test.newsapiclient.data.repository.datasourceImpl

import com.test.newsapiclient.data.api.NewsApiService
import com.test.newsapiclient.data.model.ApiResponse
import com.test.newsapiclient.data.repository.datasource.NewsRemoteDataSource
import retrofit2.Response

class NewsRemoteDataSourceImpl(
    private val newsApiService: NewsApiService
): NewsRemoteDataSource {

    override suspend fun getTopHeadLines(country: String, page: Int): Response<ApiResponse> {
        return newsApiService.getTopHeadLines(country, page)
    }

    override suspend fun getSearchedTopHeadLines(
        country: String,
        searchQuery: String,
        page: Int
    ): Response<ApiResponse> {
        return newsApiService.getSearchedTopHeadLines(country, searchQuery, page)
    }

    override suspend fun getAllNews(language: String, page: Int): Response<ApiResponse> {
        return newsApiService.getAllNews(language, page)
    }
}