package com.yashkasera.flickrgallery.di

import android.content.Context
import androidx.room.Room
import com.yashkasera.flickrgallery.BuildConfig
import com.yashkasera.flickrgallery.data.source.local.FlickrDatabase
import com.yashkasera.flickrgallery.data.source.remote.ApiHelper
import com.yashkasera.flickrgallery.data.source.remote.ApiHelperImpl
import com.yashkasera.flickrgallery.data.source.remote.ApiService
import com.yashkasera.flickrgallery.util.ApiKeyInterceptor
import com.yashkasera.flickrgallery.util.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private val loggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private val apiKeyInterceptor = ApiKeyInterceptor()
    private val okHttpClient: OkHttpClient =
        OkHttpClient.Builder().apply {
            if (BuildConfig.DEBUG)
                addInterceptor(loggingInterceptor)
        }.addInterceptor(apiKeyInterceptor).build()

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideApiHelper(apiHelper: ApiHelperImpl): ApiHelper = apiHelper

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): FlickrDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            FlickrDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

}
