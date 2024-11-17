package com.example.kotlinquizgraduationproject.ui.feature.LevelsScreen.domain.usecases

import android.util.Log
import com.example.kotlinquizgraduationproject.model.usersinfo.LevelProgress
import com.example.kotlinquizgraduationproject.repository.ApiRepository
import com.example.kotlinquizgraduationproject.repository.DBRepository
import com.example.kotlinquizgraduationproject.repository.SharedPreferencesRepository
import com.example.kotlinquizgraduationproject.ui.feature.LevelsScreen.domain.LevelsResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoadLevelProgressUseCase @Inject constructor(
    private val dbRepository: DBRepository
) {

    fun loadLevelProgress(): Flow<LevelsResult> {
        return flow {
            //TEST
            Log.e("SharedPreferencesRepository", "USER = "+SharedPreferencesRepository.getUserId())
            val responseUser = dbRepository.getAllUsers()
            Log.e("BD USER", responseUser.toString())
            //TEST

            val responseProgress = dbRepository.getProgress(SharedPreferencesRepository.getUserId())
            val levelProgress = responseProgress.map { entity -> LevelProgress(entity.category, entity.difficulty, entity.progress) }
            Log.e("loadLevelsProgress", levelProgress.toString())
            emit(LevelsResult.LevelsProgressLoaded(levelProgress))
        }
    }

}