package com.example.kotlinquizgraduationproject.ui.feature.LevelsScreen.domain.usecases

import com.example.kotlinquizgraduationproject.repository.ApiRepository
import com.example.kotlinquizgraduationproject.repository.DBRepository
import com.example.kotlinquizgraduationproject.ui.feature.LevelsScreen.domain.LevelsResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoadLevelProgressUseCase @Inject constructor(
    private val dbRepository: DBRepository
) {

    fun loadListOfQuestionCategories(): Flow<LevelsResult> {
        return flow {
            emit(LevelsResult.Loading)

        }
    }

}