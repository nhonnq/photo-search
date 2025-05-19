package dev.nhonnq.domain.usecase

import dev.nhonnq.domain.entities.PhotoDetails
import dev.nhonnq.domain.repository.PhotoRepository
import dev.nhonnq.domain.util.Result

/**
 * Use case for retrieving photo details from the repository.
 *
 * @property repository The repository that provides photo data.
 */
class GetPhotoDetailsUseCase(private val repository: PhotoRepository) {
    suspend operator fun invoke(photoId: Int): Result<PhotoDetails> = repository.getPhoto(id = photoId)
}
