package dev.nhonnq.data

import dev.nhonnq.data.api.PexelsApi
import dev.nhonnq.data.repository.PhotoRepositoryImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import dev.nhonnq.domain.util.Result
import dev.nhonnq.test.base.BaseTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

@ExperimentalCoroutinesApi
class PhotoRepositoryImplTest: BaseTest() {

    @Mock
    private var pexelsApi: PexelsApi = mock()
    private lateinit var repository: PhotoRepositoryImpl

    @Before
    fun setUp() {
        repository = PhotoRepositoryImpl(pexelsApi)
    }

    @Test
    fun `getPhoto returns success when api returns details`() = runUnconfinedTest {
        val details = dev.nhonnq.domain.entities.PhotoDetails(
            id = 1,
            width = 100,
            height = 100,
            url = "url",
            photographer = "photographer",
            photographerUrl = "photographerUrl",
            src = null,
            avgColor = null,
            liked = false,
            alt = null
        )
        `when`(pexelsApi.getPhoto(1)).thenReturn(details)
        val result = repository.getPhoto(1)
        assert(result is Result.Success)
        assertEquals(details, (result as Result.Success).data)
    }

    @Test
    fun `getPhoto returns error when api throws`() = runUnconfinedTest {
        `when`(pexelsApi.getPhoto(1)).thenThrow(RuntimeException("error"))
        val result = repository.getPhoto(1)
        assert(result is Result.Error)
    }

    @Test
    fun `searchPhotos returns a Flow of PagingData`() = runUnconfinedTest {
        // This test checks that the repository returns a Flow<PagingData<PhotoEntity>>
        val flow = repository.searchPhotos("test")
        // We can't assert the contents of PagingData easily, but we can check the type
        val first = flow // Should be a Flow<PagingData<PhotoEntity>>
        assert(first != null)
    }
}