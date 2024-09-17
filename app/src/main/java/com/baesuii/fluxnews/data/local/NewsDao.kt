package com.baesuii.fluxnews.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.baesuii.fluxnews.domain.model.Article
import kotlinx.coroutines.flow.Flow

//Data Access Object (DAO) interface for the Article entity
@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(article: Article)

    @Delete
    suspend fun delete(article: Article)

    @Query("SELECT * FROM Article")
    fun getArticles(): Flow<List<Article>>

    @Query("SELECT * FROM Article WHERE url=:url")
    suspend fun getArticle(url: String): Article?

}