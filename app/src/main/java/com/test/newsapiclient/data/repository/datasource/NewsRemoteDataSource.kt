package com.test.newsapiclient.data.repository.datasource

import com.test.newsapiclient.data.model.ApiResponse
import retrofit2.Response

interface NewsRemoteDataSource {
    suspend fun getTopHeadLines(country: String, page: Int):Response<ApiResponse>
    suspend fun getSearchedTopHeadLines(country: String, searchQuery: String, page: Int):Response<ApiResponse>
    suspend fun getAllNews(language: String, page: Int):Response<ApiResponse>
}