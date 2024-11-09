package com.example.kotlinquizgraduationproject.network

import com.example.kotlinquizgraduationproject.network.entity.Catefories.MetadataResponse
import com.example.kotlinquizgraduationproject.network.entity.Questions.ListQuestionsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface QuizApi {

    @GET("questions")
    suspend fun getListQuestions(
        @Query("difficulties") difficulties: String,
        @Query("categories") categories: String
    ): Response<ListQuestionsResponse>

    @GET("metadata")
    suspend fun getListOfQuestionCategories(): Response<MetadataResponse>


}