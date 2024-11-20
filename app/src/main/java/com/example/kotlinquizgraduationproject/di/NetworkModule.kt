package com.example.kotlinquizgraduationproject.di

import android.content.Context
import com.example.kotlinquizgraduationproject.R
import com.example.kotlinquizgraduationproject.network.QuizApi
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
class NetworkModule {

    @Provides
    @Singleton
    fun provideQuizApi(@ApplicationContext context: Context): QuizApi {
        val retrofit =
            Retrofit
                .Builder()
                .baseUrl(context.getString(R.string.baseUrl))
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