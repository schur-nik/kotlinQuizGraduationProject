package com.example.kotlinquizgraduationproject.model

data class Question(
    val correctAnswer: String,
    val incorrectAnswers: List<String>,
    val question: String
)
