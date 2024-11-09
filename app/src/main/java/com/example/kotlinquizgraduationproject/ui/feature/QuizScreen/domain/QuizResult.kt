package com.example.kotlinquizgraduationproject.ui.feature.QuizScreen.domain

import com.example.kotlinquizgraduationproject.model.Question

sealed class QuizResult {

    data class QuestionListLoaded(val list: List<Question>) : QuizResult()

    data object Loading : QuizResult()

    data class Failure(val error: Throwable? = null) : QuizResult()

}