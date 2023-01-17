package com.test.newsapiclient.domain.usecase

import com.test.newsapiclient.data.model.Article
import com.test.newsapiclient.domain.repository.NewsRepository
import kotlinx.coroutines.newSingleThreadContext

class DeleteSavedNewsUseCase(private val newsRepository: NewsRepository) {

    suspend fun execute(article: Article) = newsRepository.deleteNews(article)
}