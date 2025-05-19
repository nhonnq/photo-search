package dev.nhonnq.data.repository

import dev.nhonnq.data.db.dao.SearchHistoryDao
import dev.nhonnq.data.entities.SearchHistory
import dev.nhonnq.data.exception.DataNotAvailableException
import dev.nhonnq.domain.util.Result

/**
 * Local implementation of [ISearchDataSource] that fetches data from the local database.
 */
class SearchLocalDataSource(
    private val searchHistoryDao: SearchHistoryDao
) : ISearchDataSource {

    override suspend fun getAllHistories(): Result<List<SearchHistory>?> {
        return searchHistoryDao.getAllHistories()?.let {
            Result.Success(it)
        } ?: Result.Error(DataNotAvailableException())
    }

    override suspend fun saveHistory(searchHistory: SearchHistory) {
        searchHistoryDao.insertHistory(searchHistory)
    }

    override suspend fun clearHistory() {
        searchHistoryDao.clearHistory()
    }
}
