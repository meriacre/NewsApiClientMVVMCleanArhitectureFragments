package com.test.newsapiclient.domain.repository

import com.test.newsapiclient.data.model.ApiResponse
import com.test.newsapiclient.data.model.Article
import com.test.newsapiclient.data.util.Resource
import kotlinx.coroutines.flow.Flow

interface NewsRepository{

    suspend fun getNewsHeadlines(country: String, page: Int): Resource<ApiResponse>
    suspend fun getSearchedNews(country: String, searchQuery:String, page: Int) : Resource<ApiResponse>
    suspend fun saveNews(article: Article)
    suspend fun deleteNews(article: Article)
    fun getSavedNews(): Flow<List<Article>>
    suspend fun getAllNews(language: String, page: Int): Resource<ApiResponse>

}