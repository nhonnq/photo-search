package dev.nhonnq.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.nhonnq.data.db.dao.SearchHistoryDao
import dev.nhonnq.data.entities.SearchHistory

/**
 * [PhotoDatabase] is the main database class for the application.
 * It extends [RoomDatabase] and serves as the main access point to the underlying SQLite database.
 *
 * This class contains the database configuration and serves as the main access point for the
 * underlying SQLite database. It includes the entities and version information.
 *
 * @see SearchHistoryDao
 */
@Database(
    entities = [SearchHistory::class],
    version = 1,
    exportSchema = false
)
abstract class PhotoDatabase : RoomDatabase() {
    abstract fun searchHistoryDao(): SearchHistoryDao
}