package com.example.kotlinquizgraduationproject.ui.feature.QuizScreen.domain.usecases

import com.example.kotlinquizgraduationproject.model.LevelInformation
import com.example.kotlinquizgraduationproject.model.Question
import com.example.kotlinquizgraduationproject.repository.QuizRepository
import com.example.kotlinquizgraduationproject.ui.feature.QuizScreen.domain.QuizResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoadQuestionsUseCase @Inject constructor(
    private val quizRepository: QuizRepository
) {

    fun loadQuestionList(levelInformation: LevelInformation): Flow<QuizResult> {
        return flow {
            emit(QuizResult.Loading)
            val responseQuestionForLevel = quizRepository.getListQuestions(levelInformation)
            if (responseQuestionForLevel.isSuccessful) {
                val questions = responseQuestionForLevel.body()?.map { questionItem ->
                    Question(
                        correctAnswer = questionItem.correctAnswer,
                        allAnswers = (questionItem.incorrectAnswers+questionItem.correctAnswer).shuffled(),
                        question = questionItem.question.text
                    )
                } ?: emptyList()
                emit(QuizResult.QuestionListLoaded(questions))
            } else {
                emit(QuizResult.Failure())
            }
        }.catch {
            emit(QuizResult.Failure(it))
        }
    }

}