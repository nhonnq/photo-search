package dev.nhonnq.app.viewmodels

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import dev.nhonnq.app.ui.photo.details.state.PhotoDetailsBundle
import dev.nhonnq.app.ui.photo.details.state.PhotoDetailsState
import dev.nhonnq.app.ui.photo.details.viewmodel.PhotoDetailsViewModel
import dev.nhonnq.domain.entities.PhotoDetails
import dev.nhonnq.domain.usecase.GetPhotoDetailsUseCase
import dev.nhonnq.domain.util.Result
import dev.nhonnq.test.base.BaseTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

class PhotoDetailsViewModelTest : BaseTest() {

    @Mock
    private lateinit var getPhotoDetailsUseCase: GetPhotoDetailsUseCase

    private lateinit var viewModel: PhotoDetailsViewModel

    private val photoDetailsBundle: PhotoDetailsBundle = mock()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        getPhotoDetailsUseCase = mock()
    }

    @Test
    fun `test ui state reflects user details correctly`() = runUnconfinedTest {
        val photoId = 1
        val photo = PhotoDetails(
            id = photoId,
            width = 1024,
            height = 1024,
            url = "https://www.pexels.com/photo/hot-air-balloon-2325447/",
            photographer = "John Doe 1",
            src = null
        )

        createViewModel(
            photoId = photoId,
            photoDetailsResult = Result.Success(photo)
        )

        viewModel.uiState.test {
            val emission = awaitItem()
            assertThat(emission.photoData?.id).isEqualTo(photo.id)
            assertThat(emission.photoData?.width).isEqualTo(photo.width)
            assertThat(emission.photoData?.height).isEqualTo(photo.height)
            assertThat(emission.photoData?.url).isEqualTo(photo.url)
            assertThat(emission.photoData?.photographer).isEqualTo(photo.photographer)
        }
    }

    private suspend fun createViewModel(
        photoId: Int,
        photoDetailsResult: Result<PhotoDetails>,
    ) {
        whenever(photoDetailsBundle.photoId).thenReturn(photoId)
        whenever(getPhotoDetailsUseCase(photoId)).thenReturn(photoDetailsResult)

        viewModel = PhotoDetailsViewModel(
            getPhotoDetailsUseCase = getPhotoDetailsUseCase,
            photoDetailsBundle = photoDetailsBundle
        )
    }

    @Test
    fun `test no change in UI when photo id is invalid`() = runUnconfinedTest {
        createViewModel(
            photoId = 1,
            photoDetailsResult = Result.Error(mock())
        )

        viewModel.uiState.test {
            val emission = awaitItem()
            assertThat(emission).isEqualTo(PhotoDetailsState())
        }
    }
}