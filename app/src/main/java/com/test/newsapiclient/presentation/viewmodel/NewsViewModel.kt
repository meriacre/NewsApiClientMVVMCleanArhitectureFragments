package com.test.newsapiclient.presentation.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.*
import com.test.newsapiclient.data.model.ApiResponse
import com.test.newsapiclient.data.model.Article
import com.test.newsapiclient.data.util.Resource
import com.test.newsapiclient.domain.usecase.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.time.format.ResolverStyle

class NewsViewModel(
    private val app: Application,
    private val getNewsHeadlinesUseCase: GetNewsHeadlinesUseCase,
    private val getSearchedNewsUseCase: GetSearchedNewsUseCase,
    private val saveNewsUseCase: SaveNewsUseCase,
    private val getSavedNewsUseCase: GetSavedNewsUseCase,
    private val deleteSavedNewsUseCase: DeleteSavedNewsUseCase
): AndroidViewModel(app) {

    // get news from api

    val newsHeadLines: MutableLiveData<Resource<ApiResponse>> = MutableLiveData()

    fun getNewsHeadLines(country: String, page: Int) = viewModelScope.launch(Dispatchers.IO){
        newsHeadLines.postValue(Resource.Loading())
        try {
        if (isNetworkAvailable(app)){
        val apiResult = getNewsHeadlinesUseCase.execute(country, page)
        newsHeadLines.postValue(apiResult)
        }else{
            newsHeadLines.postValue(Resource.Error("Internet is not available"))
        }

        }catch (e:Exception){
            newsHeadLines.postValue(Resource.Error(e.message.toString()))
        }
    }

    private fun isNetworkAvailable(context: Context): Boolean{
        var result = false
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        cm?.run {
            cm.getNetworkCapabilities(cm.activeNetwork)?.run {
                result = when {
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                    else -> false
                }
            }
        }
        return result
    }

    // search news

    val searchedNews: MutableLiveData<Resource<ApiResponse>> = MutableLiveData()

    fun searchNews(
        country: String,
        searchQuery:String,
        page: Int
    ) = viewModelScope.launch {
        searchedNews.postValue(Resource.Loading())
        try {
        if (isNetworkAvailable(app)){
            val response = getSearchedNewsUseCase.execute(country, searchQuery, page)
            searchedNews.postValue(response)
        }else{
            searchedNews.postValue(Resource.Error("No Internet Connection"))
        }
        }catch (e:Exception){
            searchedNews.postValue(Resource.Error(e.message.toString()))
        }
    }

    // local data

    fun saveArticle(article: Article) = viewModelScope.launch {
        saveNewsUseCase.execute(article)
    }

    fun getSavedNews() = liveData{
        getSavedNewsUseCase.execute().collect{
            emit(it)
        }
    }

    //delete article

    fun deleteArticle(article: Article) = viewModelScope.launch {
        deleteSavedNewsUseCase.execute(article)
    }

}