package com.example.kotlinquizgraduationproject.ui.feature.QuizScreen.domain

import com.example.kotlinquizgraduationproject.model.quizinfo.LevelInformation
import com.example.kotlinquizgraduationproject.model.quizinfo.Question

sealed class QuizResult {

    data class QuestionListLoaded(val list: List<Question>, val levelInformation: LevelInformation) : QuizResult()

    data object Loading : QuizResult()

    data class Failure(val error: Throwable? = null) : QuizResult()

    data class QuestionDone(val answer: String, val isCorrect: Boolean) : QuizResult()

    data class QuestionNext(val currentNumber: Int) : QuizResult()

    data object FinishQuiz : QuizResult()

}