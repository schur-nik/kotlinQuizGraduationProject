package com.example.kotlinquizgraduationproject.network.entity.Questions

import com.google.gson.annotations.SerializedName

data class QuestionItem(
    @SerializedName("correctAnswer")
    val correctAnswer: String,
    @SerializedName("incorrectAnswers")
    val incorrectAnswers: List<String>,
    @SerializedName("question")
    val question: QuestionText
)