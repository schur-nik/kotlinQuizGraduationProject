package com.example.kotlinquizgraduationproject.ui.fakePackage

import com.example.kotlinquizgraduationproject.network.QuizApi
import com.example.kotlinquizgraduationproject.network.entity.Categories.MetadataResponse
import com.example.kotlinquizgraduationproject.network.entity.Questions.ListQuestionsResponse
import retrofit2.Response

class FakeQuizApi : QuizApi {
    override suspend fun getListQuestions(
        difficulties: String,
        categories: String
    ): Response<ListQuestionsResponse> {
        return Response.success(ListQuestionsResponse())
    }

    override suspend fun getListOfQuestionCategories(): Response<MetadataResponse> {
        return TODO("Provide the return value")
    }
}