package com.example.kotlinquizgraduationproject.ui.feature.QuizScreen.domain

import com.example.kotlinquizgraduationproject.model.quizinfo.Question

data class QuizState(
    val isLoading: Boolean = false,
    val questionList: List<Question> = arrayListOf(),
    val currentQuestion: Question? = null,
    val currentNumber: Int = 0,
    val userAnswer: String? = null,
    val endQuiz: Boolean = false
)
