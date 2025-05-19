package dev.nhonnq.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import dev.nhonnq.data.api.PexelsApi
import dev.nhonnq.data.util.DataConstants.PAGE_SIZE
import dev.nhonnq.data.util.safeApiCall
import dev.nhonnq.domain.entities.PhotoDetails
import dev.nhonnq.domain.entities.PhotoEntity
import dev.nhonnq.domain.repository.PhotoRepository
import dev.nhonnq.domain.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Implementation of [PhotoRepository] that coordinates between remote and local data sources,
 * and manages pagination using [PexelsPagingSource].
 */
class PhotoRepositoryImpl(
    private val api: PexelsApi
) : PhotoRepository {

    override fun searchPhotos(query: String, page: Int?, perPage: Int?): Flow<PagingData<PhotoEntity>> {
        return Pager(PagingConfig(pageSize = PAGE_SIZE)) {
            PexelsPagingSource(api, query)
        }.flow.map { pagingData ->
            pagingData
        }
    }

    override suspend fun getPhoto(id: Int): Result<PhotoDetails> = safeApiCall {
        api.getPhoto(id)
    }

}
