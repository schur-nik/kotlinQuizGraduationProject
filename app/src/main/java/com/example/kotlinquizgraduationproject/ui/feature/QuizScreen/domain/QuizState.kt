package com.example.kotlinquizgraduationproject.ui.feature.QuizScreen.domain

data class QuizState(
    val isLoading: Boolean = false,
    val listCategory: List<String> = arrayListOf(),
)
