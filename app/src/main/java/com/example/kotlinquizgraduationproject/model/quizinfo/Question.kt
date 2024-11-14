package com.example.kotlinquizgraduationproject.model.quizinfo

data class Question(
    val correctAnswer: String,
    val allAnswers: List<String>,
    val question: String
)
