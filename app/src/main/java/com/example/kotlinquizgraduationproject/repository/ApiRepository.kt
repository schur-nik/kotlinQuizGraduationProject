package com.example.kotlinquizgraduationproject.repository

import com.example.kotlinquizgraduationproject.model.quizinfo.LevelInformation
import com.example.kotlinquizgraduationproject.network.QuizApi
import javax.inject.Inject

class ApiRepository @Inject constructor(
    private val quizApi: QuizApi
) {

    suspend fun getListQuestions(levelInformation: LevelInformation) = quizApi.getListQuestions(levelInformation.difficulty.orEmpty(), levelInformation.category.orEmpty())

    suspend fun getListOfQuestionCategories() = quizApi.getListOfQuestionCategories()

}