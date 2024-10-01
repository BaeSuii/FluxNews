package com.baesuii.fluxnews.domain.use_case

import androidx.paging.PagingData
import com.baesuii.fluxnews.data.local.NewsDao
import com.baesuii.fluxnews.domain.model.Article
import com.baesuii.fluxnews.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow


data class NewsUseCases(
    val getBreakingNews: GetBreakingNews,
    val getNewsEverything: GetNewsEverything,
    val getCategorizedNews: GetCategorizedNews,
    val searchNews: SearchNews,
    val upsertArticle: UpsertArticle,
    val deleteArticle: DeleteArticle,
    val getArticles: GetArticles,
    val getArticle: GetArticle
)

class GetBreakingNews (
    private val newsRepository: NewsRepository
) {
    operator fun invoke(country: String): Flow<PagingData<Article>> {
        return newsRepository.getBreakingNews(
            country = country
        )
    }
}

class GetNewsEverything (
    private val newsRepository: NewsRepository
) {
    operator fun invoke(sources: List<String>): Flow<PagingData<Article>> {
        return newsRepository.getNewsEverything(sources)
    }
}

class GetCategorizedNews(
    private val newsRepository: NewsRepository
) {
    operator fun invoke(category: String, sources: List<String>): Flow<PagingData<Article>> {
        return newsRepository.getCategorizedNews(category, sources)
    }
}

class SearchNews (
    private val newsRepository: NewsRepository
){
    operator fun invoke(searchQuery: String, sources: List<String>): Flow<PagingData<Article>> {
        return newsRepository.searchNews(searchQuery = searchQuery, sources = sources)
    }
}

class UpsertArticle (
    private val newsDao: NewsDao
){
    suspend operator fun invoke(article: Article){
        newsDao.upsert(article)
    }
}

class DeleteArticle (
    private val newsDao: NewsDao
){
    suspend operator fun invoke(article: Article){
        newsDao.delete(article)
    }
}

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