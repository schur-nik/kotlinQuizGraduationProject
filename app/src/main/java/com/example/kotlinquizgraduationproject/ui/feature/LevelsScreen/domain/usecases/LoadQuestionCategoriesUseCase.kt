package com.example.kotlinquizgraduationproject.ui.feature.LevelsScreen.domain.usecases

import com.example.kotlinquizgraduationproject.repository.QuizRepository
import com.example.kotlinquizgraduationproject.ui.feature.LevelsScreen.domain.LevelsResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoadQuestionCategoriesUseCase @Inject constructor(
    private val quizRepository: QuizRepository
) {

    fun loadListOfQuestionCategories(): Flow<LevelsResult> {
        return flow {
            emit(LevelsResult.Loading)
            val responseAllCategory = quizRepository.getListOfQuestionCategories()
            if (responseAllCategory.isSuccessful) {
                val categories = responseAllCategory.body()?.category?.map { categoryItem ->
                    categoryItem.key
                } ?: emptyList()
                emit(LevelsResult.QuestionCategoriesListLoaded(categories))
            } else {
                emit(LevelsResult.Failure())
            }
        }.catch {
            emit(LevelsResult.Failure(it))
        }
    }

}