package com.test.newsapiclient.data.api

import com.test.newsapiclient.BuildConfig
import com.test.newsapiclient.data.model.ApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET("v2/top-headlines")
    suspend fun getTopHeadLines(
        @Query("country")
        country: String,
        @Query("page")
        page: Int,
        @Query("apiKey")
        apikey: String = BuildConfig.API_KEY
    ): Response<ApiResponse>

    @GET("v2/top-headlines")
    suspend fun getSearchedTopHeadLines(
        @Query("country")
        country: String,
        @Query("q")
        searchQuery: String,
        @Query("page")
        page: Int,
        @Query("apiKey")
        apikey: String = BuildConfig.API_KEY
    ): Response<ApiResponse>

    @GET("v2/everything")
    suspend fun getAllNews(
        @Query("q")
        language: String,
        @Query("page")
        page: Int,
        @Query("apiKey")
        apikey: String = BuildConfig.API_KEY
    ): Response<ApiResponse>
}