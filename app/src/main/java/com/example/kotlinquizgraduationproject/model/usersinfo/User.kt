package com.example.kotlinquizgraduationproject.model.usersinfo

import com.example.kotlinquizgraduationproject.model.quizinfo.Category

data class User(
    val name: String,
    val favoriteCategory: List<Category>,
    val progress: List<LevelProgress>
)
