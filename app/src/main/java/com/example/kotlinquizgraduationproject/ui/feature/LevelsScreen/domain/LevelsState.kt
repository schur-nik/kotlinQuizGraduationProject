package com.example.kotlinquizgraduationproject.ui.feature.LevelsScreen.domain

import com.example.kotlinquizgraduationproject.model.quizinfo.Category
import com.example.kotlinquizgraduationproject.model.usersinfo.LevelProgress

data class LevelsState(
    val isLoading: Boolean = false,
    val listCategory: List<Category> = arrayListOf(),
    val listProgress: List<LevelProgress> = arrayListOf(),
    val listFavorites: List<Category> = arrayListOf(),
    val isFailure: Boolean = false
)
