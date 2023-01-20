package com.test.newsapiclient.data.repository

import com.test.newsapiclient.data.model.ApiResponse
import com.test.newsapiclient.data.model.Article
import com.test.newsapiclient.data.repository.datasource.NewsLocalDataSource
import com.test.newsapiclient.data.repository.datasource.NewsRemoteDataSource
import com.test.newsapiclient.data.util.Resource
import com.test.newsapiclient.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class NewsRepositoryImpl(
    private val newsRemoteDataSource: NewsRemoteDataSource,
    private val newsLocalDataSource: NewsLocalDataSource
): NewsRepository {
    override suspend fun getNewsHeadlines(country: String, page: Int): Resource<ApiResponse> {
        return responseToResource(newsRemoteDataSource.getTopHeadLines(country, page))
    }

    override suspend fun getSearchedNews(
        country: String,
        searchQuery: String,
        page: Int
    ): Resource<ApiResponse> {
        return responseToResource(newsRemoteDataSource.getSearchedTopHeadLines(country, searchQuery, page))
    }

    private fun responseToResource(response: Response<ApiResponse>): Resource<ApiResponse>{
        if (response.isSuccessful){
            response.body()?.let{ result ->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }

    override suspend fun saveNews(article: Article) {
        newsLocalDataSource.saveArticleToDB(article)
    }

    override suspend fun deleteNews(article: Article) {
        newsLocalDataSource.deleteArticlesFromDB(article)
    }

    override fun getSavedNews(): Flow<List<Article>> {
        return newsLocalDataSource.getSavedArticles()
    }

    override suspend fun getAllNews(language: String, page: Int): Resource<ApiResponse> {
        return responseToResource(newsRemoteDataSource.getAllNews(language, page))
    }
}