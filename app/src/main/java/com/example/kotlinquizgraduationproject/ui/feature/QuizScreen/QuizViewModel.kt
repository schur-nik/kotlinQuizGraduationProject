package com.example.kotlinquizgraduationproject.ui.feature.QuizScreen

import androidx.lifecycle.ViewModel
import com.example.kotlinquizgraduationproject.repository.QuizRepository
import com.example.kotlinquizgraduationproject.ui.feature.QuizScreen.domain.QuizState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val quizRepository: QuizRepository
) : ViewModel() {

    val state = MutableStateFlow(QuizState())

}