package dev.nhonnq.domain.usecase

import androidx.paging.PagingData
import dev.nhonnq.domain.entities.PhotoEntity
import dev.nhonnq.domain.repository.PhotoRepository
import kotlinx.coroutines.flow.Flow

/**
 * Use case for searching photos.
 *
 * @property repository The repository that provides photo data.
 */
class SearchPhotosUseCase(private val repository: PhotoRepository) {
    operator fun invoke(query: String): Flow<PagingData<PhotoEntity>> {
        return repository.searchPhotos(query = query)
    }
}
