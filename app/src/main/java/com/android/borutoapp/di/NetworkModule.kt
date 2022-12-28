package com.android.borutoapp.di

import com.android.borutoapp.data.local.BorutoDatabase
import com.android.borutoapp.data.remote.BorutoApi
import com.android.borutoapp.data.repository.RemoteDataSourceImpl
import com.android.borutoapp.domain.repository.RemoteDataSource
import com.android.borutoapp.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(okHttp: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttp)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(createLoggingInterceptor())
            .readTimeout(5, TimeUnit.SECONDS)
            .connectTimeout(5, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideBorutoApi(retrofit: Retrofit): BorutoApi {
        return retrofit.create(BorutoApi::class.java)
    }

    private fun createLoggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    fun provideRemoteDataSource(
        borutoApi: BorutoApi,
        borutoDatabase: BorutoDatabase
    ): RemoteDataSource {
        return RemoteDataSourceImpl(
            borutoApi = borutoApi,
            borutoDatabase = borutoDatabase
        )
    }
}