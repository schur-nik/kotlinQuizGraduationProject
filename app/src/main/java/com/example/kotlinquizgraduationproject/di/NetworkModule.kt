package com.example.kotlinquizgraduationproject.di

import com.example.kotlinquizgraduationproject.network.QuizApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideQuizApi(): QuizApi {
        val retrofit =
            Retrofit
                .Builder()
                .baseUrl("https://the-trivia-api.com/v2/")
                .client(
                    OkHttpClient()
                        .newBuilder()
                        .addNetworkInterceptor(HttpLoggingInterceptor().apply {
                            setLevel(
                                HttpLoggingInterceptor.Level.BODY
                            )
                        })
                        .build()
                )
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        return retrofit.create(QuizApi::class.java)

    }
}