package dev.nhonnq.data.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "search_histories",
    indices = [Index(value = ["query"], unique = true)]
)
data class SearchHistory(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val query: String,
    val createdAt: Long? = null,
)
