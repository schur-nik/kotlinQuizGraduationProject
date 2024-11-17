package com.example.kotlinquizgraduationproject.ui.feature.QuizScreen.domain.usecases

import android.util.Log
import com.example.kotlinquizgraduationproject.model.quizinfo.LevelInformation
import com.example.kotlinquizgraduationproject.model.quizinfo.Question
import com.example.kotlinquizgraduationproject.repository.ApiRepository
import com.example.kotlinquizgraduationproject.ui.feature.QuizScreen.domain.QuizResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoadQuestionsUseCase @Inject constructor(
    private val apiRepository: ApiRepository
) {

    fun loadQuestionList(levelInformation: LevelInformation): Flow<QuizResult> {
        return flow {
            emit(QuizResult.Loading)
            val responseQuestionForLevel = apiRepository.getListQuestions(levelInformation)
            if (responseQuestionForLevel.isSuccessful) {
                val questions = responseQuestionForLevel.body()?.map { questionItem ->
                    Question(
                        correctAnswer = questionItem.correctAnswer,
                        allAnswers = (questionItem.incorrectAnswers+questionItem.correctAnswer).shuffled(),
                        question = questionItem.question.text
                    )
                } ?: emptyList()
                emit(QuizResult.QuestionListLoaded(questions, levelInformation))
            } else {
                emit(QuizResult.Failure())
            }
        }.catch {
            emit(QuizResult.Failure(it))
        }
    }

}