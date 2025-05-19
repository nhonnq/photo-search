package dev.nhonnq.data

import androidx.paging.PagingSource
import dev.nhonnq.data.api.PexelsApi
import dev.nhonnq.data.repository.PexelsPagingSource
import dev.nhonnq.domain.entities.PhotoEntity
import dev.nhonnq.domain.model.PexelsResponse
import dev.nhonnq.test.base.BaseTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any

@ExperimentalCoroutinesApi
class PexelsPagingSourceTest: BaseTest() {

    @Mock
    private var pexelsApi: PexelsApi = mock()
    private lateinit var dataSource: PexelsPagingSource

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        dataSource = PexelsPagingSource(pexelsApi, "")
    }

    val mockPhotos: List<PhotoEntity> = listOf(
        PhotoEntity(
            id = 1,
            width = 1024,
            height = 1024,
            url = "https://www.pexels.com/photo/hot-air-balloon-2325447/",
            photographer = "John Doe",
            src = null
        ),
        PhotoEntity(
            id = 2,
            width = 512,
            height = 512,
            url = "https://www.pexels.com/photo/hot-air-balloon-2325447/",
            photographer = "John Doe",
            src = null
        )
    )

    @Test
    fun `load returns page when onSuccess`() = runUnconfinedTest {
        val response = PexelsResponse(
            page = 1,
            perPage = 20,
            totalResults = 2,
            photos = mockPhotos,
            nextPage = "2",
            prevPage = null
        )
        `when`(pexelsApi.searchPhotos(any(), any(), any())).thenReturn(response)

        val params = PagingSource.LoadParams.Refresh(
            key = 1,
            loadSize = 20,
            placeholdersEnabled = false
        )
        val result = dataSource.load(params)
        assertTrue(result is PagingSource.LoadResult.Page)
        val page = result as PagingSource.LoadResult.Page
        assertEquals(mockPhotos, page.data)
        assertEquals(null, page.prevKey)
        assertEquals(2, page.nextKey)
    }

    @Test
    fun `load returns error when exception thrown`() = runUnconfinedTest {
        `when`(pexelsApi.searchPhotos(any(), any(), any())).thenThrow(RuntimeException("API error"))
        val params = PagingSource.LoadParams.Refresh(
            key = 1,
            loadSize = 20,
            placeholdersEnabled = false
        )
        val result = dataSource.load(params)
        assertTrue(result is PagingSource.LoadResult.Error)
    }

}