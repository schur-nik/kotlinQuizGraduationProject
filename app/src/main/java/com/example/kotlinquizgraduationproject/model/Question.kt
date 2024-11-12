package com.example.kotlinquizgraduationproject.model

data class Question(
    val correctAnswer: String,
    val allAnswers: List<String>,
    val question: String
)
