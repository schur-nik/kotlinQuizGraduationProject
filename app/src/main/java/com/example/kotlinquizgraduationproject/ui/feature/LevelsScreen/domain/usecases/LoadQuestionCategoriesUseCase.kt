package com.example.kotlinquizgraduationproject.ui.feature.LevelsScreen.domain.usecases

import android.util.Log
import com.example.kotlinquizgraduationproject.model.quizinfo.Category
import com.example.kotlinquizgraduationproject.repository.ApiRepository
import com.example.kotlinquizgraduationproject.ui.feature.LevelsScreen.domain.LevelsResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoadQuestionCategoriesUseCase @Inject constructor(
    private val apiRepository: ApiRepository
) {

    fun loadListOfQuestionCategories(): Flow<LevelsResult> {
        return flow {
            emit(LevelsResult.Loading)
            val responseAllCategory = apiRepository.getListOfQuestionCategories()
            if (responseAllCategory.isSuccessful) {
                val categories = responseAllCategory.body()?.category?.map { categoryItem ->
                    Category(categoryItem.key)
                } ?: emptyList()
                Log.e("loadListOfQuestionCategories", categories.toString())
                emit(LevelsResult.QuestionCategoriesListLoaded(categories))
            } else {
                emit(LevelsResult.Failure())
            }
        }.catch {
            emit(LevelsResult.Failure(it))
        }
    }

}