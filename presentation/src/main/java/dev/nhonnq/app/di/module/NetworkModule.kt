package dev.nhonnq.app.di.module

import android.content.Context
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton
import dev.nhonnq.data.api.PexelsApi
import dev.nhonnq.data.util.NetworkMonitorImpl
import dev.nhonnq.domain.util.NetworkMonitor
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .baseUrl(dev.nhonnq.data.BuildConfig.BASE_URL)
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .addInterceptor {
            val request = it.request().newBuilder()
                .addHeader("Authorization", dev.nhonnq.app.BuildConfig.PEXELS_API_KEY)
                .build()
            it.proceed(request)
        }
        .build()

    @Singleton
    @Provides
    fun providePexelsApi(retrofit: Retrofit): PexelsApi {
        return retrofit.create(PexelsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideNetworkMonitor(
        @ApplicationContext context: Context
    ): NetworkMonitor = NetworkMonitorImpl(context)
}