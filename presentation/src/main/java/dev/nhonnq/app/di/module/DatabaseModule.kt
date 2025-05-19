package dev.nhonnq.app.di.module

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.nhonnq.data.db.PhotoDatabase
import dev.nhonnq.data.db.dao.SearchHistoryDao
import dev.nhonnq.data.util.DiskExecutor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun providePhotoDatabase(
        @ApplicationContext context: Context,
        diskExecutor: DiskExecutor
    ): PhotoDatabase {
        return Room
            .databaseBuilder(context, PhotoDatabase::class.java, "photos.db")
            .setQueryExecutor(diskExecutor)
            .setTransactionExecutor(diskExecutor)
            .build()
    }

    @Provides
    fun provideSearchHistoryDao(db: PhotoDatabase): SearchHistoryDao {
        return db.searchHistoryDao()
    }
}