package dev.nhonnq.data.repository

import android.provider.ContactsContract.Contacts.Photo
import androidx.paging.PagingSource
import androidx.paging.PagingState
import dev.nhonnq.data.api.PexelsApi
import dev.nhonnq.data.util.DataConstants.PAGE_SIZE
import dev.nhonnq.data.util.DataConstants.PHOTO_STARTING_PAGE_INDEX
import dev.nhonnq.data.util.safeApiCall
import dev.nhonnq.domain.entities.PhotoEntity
import dev.nhonnq.domain.model.PexelsResponse
import dev.nhonnq.domain.util.onError
import dev.nhonnq.domain.util.onSuccess

/**
 * [PexelsPagingSource] is a PagingSource implementation that fetches photos from the Pexels API.
 *
 * This class is responsible for loading paginated data from the Pexels API based on a search query.
 * It uses the Paging 3 library to handle pagination and caching of data.
 *
 * @property api The Pexels API interface for making network requests.
 * @property query The search query used to fetch photos.
 */
class PexelsPagingSource(
    private val api: PexelsApi,
    private val query: String
) : PagingSource<Int, PhotoEntity>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PhotoEntity> {
        val page = params.key ?: PHOTO_STARTING_PAGE_INDEX
        return try {
            val res = safeApiCall {
                api.searchPhotos(query, page, PAGE_SIZE)
            }
            var response: PexelsResponse? = null
            res.onSuccess {
                response = it
            }.onError {
                return LoadResult.Error(it)
            }
            LoadResult.Page(
                data = response?.photos ?: emptyList(),
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response?.photos.isNullOrEmpty() || response?.nextPage.isNullOrEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
    override fun getRefreshKey(state: PagingState<Int, PhotoEntity>) = null
}
