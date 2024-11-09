package com.example.kotlinquizgraduationproject.repository

import com.example.kotlinquizgraduationproject.model.Category
import com.example.kotlinquizgraduationproject.model.LevelInformation
import com.example.kotlinquizgraduationproject.network.QuizApi
import javax.inject.Inject

class QuizRepository @Inject constructor(
    private val quizApi: QuizApi
) {

    suspend fun getListQuestions(levelInformation: LevelInformation) = quizApi.getListQuestions(levelInformation.difficulty, levelInformation.category)

    suspend fun getListOfQuestionCategories() = quizApi.getListOfQuestionCategories()

}