package com.example.kotlinquizgraduationproject.ui.feature.LevelsScreen.domain

data class LevelsState(
    val isLoading: Boolean = false,
    val listCategory: List<String> = arrayListOf(),
)
