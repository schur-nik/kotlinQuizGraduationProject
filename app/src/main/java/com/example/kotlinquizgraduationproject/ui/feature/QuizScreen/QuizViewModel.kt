package com.example.kotlinquizgraduationproject.ui.feature.QuizScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinquizgraduationproject.model.quizinfo.LevelInformation
import com.example.kotlinquizgraduationproject.ui.feature.QuizScreen.domain.QuizAction
import com.example.kotlinquizgraduationproject.ui.feature.QuizScreen.domain.QuizResult
import com.example.kotlinquizgraduationproject.ui.feature.QuizScreen.domain.QuizState
import com.example.kotlinquizgraduationproject.ui.feature.QuizScreen.domain.usecases.AnswerQuestionUseCase
import com.example.kotlinquizgraduationproject.ui.feature.QuizScreen.domain.usecases.FinishQuizUseCase
import com.example.kotlinquizgraduationproject.ui.feature.QuizScreen.domain.usecases.LoadQuestionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val loadQuestionsUseCase: LoadQuestionsUseCase,
    private val answerQuestionUseCase: AnswerQuestionUseCase,
    private val finishQuizUseCase: FinishQuizUseCase
) : ViewModel() {

    val state = MutableStateFlow(QuizState())
    private val levelInformation = LevelInformation("", "")

    init {
        processedAction(QuizAction.Init(levelInformation))
    }

    fun processedAction(action: QuizAction) {
        viewModelScope.launch {
            when (action) {
                is QuizAction.Init -> loadQuestionsUseCase.loadQuestionList(action.levelInformation)
                is QuizAction.AnswerQuestion -> answerQuestionUseCase.answerQuestion(action.answer, state.value.currentQuestion)
                is QuizAction.NextQuestion -> flowOf(QuizResult.QuestionNext(state.value.currentNumber))
                is QuizAction.FinishQuiz -> finishQuizUseCase.finishQuiz(levelInformation, state.value.correctAnswersCount)
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
                        questionCount = result.list.size,
                        questionList = result.list,
                        currentQuestion = result.list.firstOrNull(),
                        currentNumber = 0
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
                        userAnswer = result.answer,
                        correctAnswersCount = if (result.isCorrect) {
                            state.value.correctAnswersCount.inc()
                        } else {
                            state.value.correctAnswersCount
                        }
                    )
                )
            }

            is QuizResult.QuestionNext -> {
                val nextNumber = state.value.currentNumber + 1
                state.emit(
                    state.value.copy(
                        currentNumber = nextNumber,
                        currentQuestion = state.value.questionList[nextNumber],
                        userAnswer = null
                    )
                )
            }

            is QuizResult.FinishQuiz -> {
                state.emit(
                    state.value.copy(
                        questionCount = 0,
                        questionList = arrayListOf(),
                        currentQuestion = null,
                        currentNumber = 0,
                        userAnswer = null,
                        endQuiz = true
                    )
                )
            }

        }
    }

}