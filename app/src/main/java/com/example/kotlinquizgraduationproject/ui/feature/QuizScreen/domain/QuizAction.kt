package com.example.kotlinquizgraduationproject.ui.feature.QuizScreen.domain

import com.example.kotlinquizgraduationproject.model.LevelInformation

sealed class QuizAction {

    data class Init(val levelInformation: LevelInformation) : QuizAction()

    data class AnswerQuestion(val answer: String) : QuizAction()

    data object NextQuestion : QuizAction()

}