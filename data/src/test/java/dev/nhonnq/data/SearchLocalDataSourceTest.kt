package dev.nhonnq.data

import dev.nhonnq.data.db.dao.SearchHistoryDao
import dev.nhonnq.data.entities.SearchHistory
import dev.nhonnq.data.repository.SearchLocalDataSource
import dev.nhonnq.test.base.BaseTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class SearchLocalDataSourceTest: BaseTest() {

    private lateinit var searchHistoryDao: SearchHistoryDao
    private lateinit var searchLocalDataSource: SearchLocalDataSource

    @Before
    fun setUp() {
        searchHistoryDao = mock(SearchHistoryDao::class.java)
        searchLocalDataSource = SearchLocalDataSource(searchHistoryDao)
    }

    @Test
    fun `test getAllSearchHistory`() = runUnconfinedTest {
        searchLocalDataSource.getAllHistories()
        verify(searchHistoryDao).getAllHistories()
    }

    @Test
    fun `test insertSearchHistory`() = runUnconfinedTest {
        val searchHistory = SearchHistory(id = 1, query = "test")
        searchLocalDataSource.saveHistory(searchHistory)
        verify(searchHistoryDao).insertHistory(searchHistory)
    }

    @Test
    fun `test clearAllHistories`() = runUnconfinedTest {
        searchLocalDataSource.clearHistory()
        verify(searchHistoryDao).clearHistory()
    }

}