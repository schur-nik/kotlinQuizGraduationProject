package com.example.kotlinquizgraduationproject.ui.feature.QuizScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinquizgraduationproject.model.LevelInformation
import com.example.kotlinquizgraduationproject.ui.feature.QuizScreen.domain.QuizAction
import com.example.kotlinquizgraduationproject.ui.feature.QuizScreen.domain.QuizResult
import com.example.kotlinquizgraduationproject.ui.feature.QuizScreen.domain.QuizState
import com.example.kotlinquizgraduationproject.ui.feature.QuizScreen.domain.usecases.LoadQuestionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val loadQuestionsUseCase: LoadQuestionsUseCase
) : ViewModel() {

    val state = MutableStateFlow(QuizState())

    init {
        processedAction(QuizAction.Init(LevelInformation(null, null)))
    }

    fun processedAction(action: QuizAction) {
        viewModelScope.launch {
            when (action) {
                is QuizAction.Init -> loadQuestionsUseCase.loadQuestionList(action.levelInformation)
                is QuizAction.AnswerQuestion -> flowOf(QuizResult.QuestionDone(action.answer))
                is QuizAction.NextQuestion -> flowOf(QuizResult.QuestionNext(state.value.currentNumber))
            }.collect { result ->
                handleResult(result)
            }
        }
    }

    private suspend fun handleResult(result: QuizResult) {
        when (result) {
            is QuizResult.Loading -> {
                state.emit(state.value.copy(isLoading = true))
            }

            is QuizResult.QuestionListLoaded -> {
                state.emit(
                    state.value.copy(
                        isLoading = false,
                        questionList = result.list,
                        currentQuestion = result.list.firstOrNull()
                    )
                )
            }

            is QuizResult.Failure -> {
                state.emit(
                    state.value.copy(
                        isLoading = false
                    )
                )
            }

            is QuizResult.QuestionDone -> {
                state.emit(
                    state.value.copy(
                        userAnswer = result.answer
                    )
                )
            }

            is QuizResult.QuestionNext -> {
                Log.e("state.value.questionList.size", state.value.questionList.size.toString())
                Log.e("state.value.currentNumber", state.value.currentNumber.toString())
                if (state.value.questionList.size <= state.value.currentNumber+1)
                {
                    state.emit(
                        state.value.copy(
//                            currentNumber = state.value.currentNumber + 1,
//                            currentQuestion = result.question,
//                            userAnswer = null,
                            endQuiz = true
                        )
                    )
                }
                else {
                    state.emit(
                        state.value.copy(
                            currentNumber = state.value.currentNumber + 1,
                            currentQuestion = state.value.questionList[state.value.currentNumber + 1],
                            userAnswer = null
                        )
                    )
                }
            }
        }
    }

}