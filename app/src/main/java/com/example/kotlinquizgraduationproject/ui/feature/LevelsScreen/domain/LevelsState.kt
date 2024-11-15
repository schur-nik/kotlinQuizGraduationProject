package com.example.kotlinquizgraduationproject.ui.feature.LevelsScreen.domain

import com.example.kotlinquizgraduationproject.model.quizinfo.Category

data class LevelsState(
    val isLoading: Boolean = false,
    val listCategory: List<Category> = arrayListOf(),
)
