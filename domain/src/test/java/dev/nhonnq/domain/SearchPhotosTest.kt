package dev.nhonnq.domain

import androidx.paging.PagingData
import dev.nhonnq.domain.entities.PhotoEntity
import dev.nhonnq.domain.repository.PhotoRepository
import dev.nhonnq.domain.usecase.SearchPhotosUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class SearchPhotosTest {

    @Mock
    private lateinit var photoRepository: PhotoRepository
    private lateinit var searchPhotosUseCase: SearchPhotosUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        searchPhotosUseCase = SearchPhotosUseCase(photoRepository)
    }

    @Test
    fun `invoke should return photo list`() = runBlocking {
        val query = "nature"
        val photoList = listOf(
            PhotoEntity(
                id= 1,
                width = 1024,
                height = 1024,
                url = "https://www.pexels.com/photo/hot-air-balloon-2325447/",
                photographer = "John Doe 1",
                src = null
            ),
            PhotoEntity(
                id= 2,
                width = 512,
                height = 512,
                url = "https://www.pexels.com/photo/hot-air-balloon-2325447/",
                photographer = "John Doe 2",
                src = null
            )
        )
        val pagingData = PagingData.from(photoList)
        val flow: Flow<PagingData<PhotoEntity>> = flowOf(pagingData)
        Mockito.`when`(photoRepository.searchPhotos(query)).thenReturn(flow)

        val result = searchPhotosUseCase(query)

        assertEquals(flow, result)
    }
}
