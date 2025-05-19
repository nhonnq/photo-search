package dev.nhonnq.domain

import dev.nhonnq.domain.entities.PhotoDetails
import dev.nhonnq.domain.repository.PhotoRepository
import dev.nhonnq.domain.usecase.GetPhotoDetailsUseCase
import dev.nhonnq.domain.util.Result
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class GetPhotoDetailsTest {

    @Mock
    private lateinit var photoRepository: PhotoRepository
    private lateinit var getPhotoDetailsUseCase: GetPhotoDetailsUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        getPhotoDetailsUseCase = GetPhotoDetailsUseCase(photoRepository)
    }

    @Test
    fun `invoke should return photo details when repository returns success`() = runBlocking {
        val photo = PhotoDetails(
            id= 1,
            width = 1024,
            height = 1024,
            url = "https://www.pexels.com/photo/hot-air-balloon-2325447/",
            photographer = "John Doe 1",
            src = null
        )
        Mockito.`when`(photoRepository.getPhoto(photo.id)).thenReturn(Result.Success(photo))

        val result = getPhotoDetailsUseCase(photo.id)

        assertEquals(Result.Success(photo), result)
    }

    @Test
    fun `invoke should return error when repository returns error`() = runBlocking {
        val photoId = 404
        val exception = Exception("Error")
        Mockito.`when`(photoRepository.getPhoto(photoId)).thenReturn(Result.Error(exception))

        val result = getPhotoDetailsUseCase(photoId)

        assertEquals(Result.Error<PhotoDetails>(exception), result)
    }
}
