package com.baesuii.fluxnews.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.baesuii.fluxnews.data.local.NewsDao
import com.baesuii.fluxnews.data.local.NewsDatabase
import com.baesuii.fluxnews.domain.model.Article
import kotlinx.coroutines.test.runTest
import app.cash.turbine.test
import com.baesuii.fluxnews.data.local.NewsTypeConverter
import com.baesuii.fluxnews.domain.model.Source
import com.google.common.truth.Truth.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
@SmallTest
class NewsDaoTest {
    private lateinit var newsDao: NewsDao
    private lateinit var db: NewsDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, NewsDatabase::class.java)
            .allowMainThreadQueries()
            .addTypeConverter(NewsTypeConverter())
            .build()
        newsDao = db.newsDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        if (::db.isInitialized) {
            db.close()
        }
    }

    @Test
    fun newsDao_upsertArticle_insertsOrReplacesArticle() = runTest {
        // Given
        val article = sampleArticle1()

        // When
        newsDao.upsert(article)

        // Then
        newsDao.getArticles().test {
            val result = awaitItem().first()
            assertThat(result).isEqualTo(article)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun newsDao_deleteArticle_removesArticle() = runTest {
        // Given
        val article = sampleArticle1()

        // When
        newsDao.upsert(article)
        newsDao.delete(article)

        // Then
        newsDao.getArticles().test {
            val result = awaitItem()
            assertThat(result).isEmpty()
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun newsDao_getArticle_returnsCorrectArticle() = runTest {
        // Given
        val article = sampleArticle1()

        // When
        newsDao.upsert(article)
        val fetchedArticle = newsDao.getArticle(article.url)

        // Then
        assertThat(fetchedArticle).isEqualTo(article)
    }

    @Test
    fun newsDao_getArticles_returnsAllArticles() = runTest {
        // Given
        val article1 = sampleArticle1()
        val article2 = sampleArticle2()

        // When
        newsDao.upsert(article1)
        newsDao.upsert(article2)

        // Then
        newsDao.getArticles().test {
            val articles = awaitItem()
            assertThat(articles).containsExactly(article1, article2)
            cancelAndConsumeRemainingEvents()
        }
    }

    private fun sampleArticle1(): Article {
        return Article(
            source = Source(id = "", name = "Wired"),
            author = "Author",
            title = "This is title 1",
            description = "This is description 1",
            url = "https://www.wired.com/article1",
            urlToImage = "https://media.wired.com/photos/article1.jpg",
            publishedAt = "2 hours ago",
            content = "This is content for article 1."
        )
    }

    private fun sampleArticle2(): Article {
        return Article(
            source = Source(id = "", name = "Wired"),
            author = "Author",
            title = "This is title 2",
            description = "This is description 2",
            url = "https://www.wired.com/article2",
            urlToImage = "https://media.wired.com/photos/article2.jpg",
            publishedAt = "2 hours ago",
            content = "This is content for article 2."
        )
    }
}
