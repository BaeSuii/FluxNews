package com.baesuii.fluxnews.domain.use_case.news

import com.baesuii.fluxnews.data.local.NewsDao
import com.baesuii.fluxnews.domain.model.Article
import kotlinx.coroutines.flow.Flow

class GetArticles(
    private val newsDao: NewsDao
) {
    operator fun invoke(): Flow<List<Article>> {
        return newsDao.getArticles()
    }

}

class GetArticle (
    private val newsDao: NewsDao
) {
    suspend operator fun invoke(url: String): Article?{
        return newsDao.getArticle(url = url)
    }
}