package com.baesuii.fluxnews.domain.use_case.news

import com.baesuii.fluxnews.data.local.NewsDao
import com.baesuii.fluxnews.domain.model.Article

class UpsertArticle (
    private val newsDao: NewsDao
){
    suspend operator fun invoke(article: Article){
        newsDao.upsert(article)
    }
}