package dev.nhonnq.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.nhonnq.data.entities.SearchHistory

@Dao
interface SearchHistoryDao {

    /**
     * Get all histories
     * **/
    @Query("SELECT * FROM search_histories")
    suspend fun getAllHistories(): List<SearchHistory>?

    /**
     * Insert history
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(history: SearchHistory)

    /**
     * Delete all search histories
     */
    @Query("DELETE FROM search_histories")
    suspend fun clearHistory()
}