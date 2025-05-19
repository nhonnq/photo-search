package dev.nhonnq.domain.repository

import androidx.paging.PagingData
import dev.nhonnq.domain.entities.PhotoDetails
import dev.nhonnq.domain.entities.PhotoEntity
import dev.nhonnq.domain.util.Result
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for Photo related operations.
 */
interface PhotoRepository {
    fun searchPhotos(query: String, page: Int? = 1, perPage: Int? = 20): Flow<PagingData<PhotoEntity>>
    suspend fun getPhoto(id: Int): Result<PhotoDetails>
}