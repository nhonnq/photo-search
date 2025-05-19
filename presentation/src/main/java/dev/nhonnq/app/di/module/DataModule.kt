package dev.nhonnq.app.di.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.nhonnq.data.api.PexelsApi
import dev.nhonnq.data.db.dao.SearchHistoryDao
import dev.nhonnq.data.repository.PhotoRepositoryImpl
import dev.nhonnq.data.repository.SearchLocalDataSource
import dev.nhonnq.data.usercase.GetAllHistories
import dev.nhonnq.data.usercase.SaveHistory
import dev.nhonnq.domain.repository.PhotoRepository
import dev.nhonnq.domain.usecase.GetPhotoDetailsUseCase
import dev.nhonnq.domain.usecase.SearchPhotosUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    fun providePhotoRepository(api: PexelsApi): PhotoRepository {
        return PhotoRepositoryImpl(api)
    }

    @Provides
    fun provideSearchPhotosUseCase(photoRepository: PhotoRepository): SearchPhotosUseCase {
        return SearchPhotosUseCase(photoRepository)
    }

    @Provides
    fun provideGetPhotoDetailsUseCase(photoRepository: PhotoRepository): GetPhotoDetailsUseCase {
        return GetPhotoDetailsUseCase(photoRepository)
    }

    @Provides
    @Singleton
    fun provideSearchLocalDataSource(
        searchHistoryDao: SearchHistoryDao
    ): SearchLocalDataSource {
        return SearchLocalDataSource(searchHistoryDao)
    }

    @Provides
    fun provideGetAllHistoriesUseCase(searchLocalDataSource: SearchLocalDataSource): GetAllHistories {
        return GetAllHistories(searchLocalDataSource)
    }

    @Provides
    fun provideSaveHistoryUseCase(searchLocalDataSource: SearchLocalDataSource): SaveHistory {
        return SaveHistory(searchLocalDataSource)
    }

}