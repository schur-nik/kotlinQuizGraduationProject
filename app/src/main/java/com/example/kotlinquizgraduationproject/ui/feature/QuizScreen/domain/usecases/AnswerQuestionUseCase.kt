package com.example.kotlinquizgraduationproject.ui.feature.QuizScreen.domain.usecases

import com.example.kotlinquizgraduationproject.model.quizinfo.Question
import com.example.kotlinquizgraduationproject.ui.feature.QuizScreen.domain.QuizResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AnswerQuestionUseCase @Inject constructor(
) {

    fun answerQuestion(userAnswer: String, currentQuestion: Question?): Flow<QuizResult> {
        val correctAnswersCount = currentQuestion?.correctAnswer == userAnswer
        return flow {
            emit(QuizResult.QuestionDone(userAnswer, correctAnswersCount))
        }
    }

}