package com.baesuii.fluxnews.domain.use_case.news


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
