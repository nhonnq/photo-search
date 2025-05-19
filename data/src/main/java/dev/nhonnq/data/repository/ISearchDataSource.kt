package dev.nhonnq.data.repository

import dev.nhonnq.data.entities.SearchHistory
import dev.nhonnq.domain.util.Result

interface ISearchDataSource {
    suspend fun getAllHistories(): Result<List<SearchHistory>?>
    suspend fun saveHistory(searchHistory: SearchHistory)
    suspend fun clearHistory()
}