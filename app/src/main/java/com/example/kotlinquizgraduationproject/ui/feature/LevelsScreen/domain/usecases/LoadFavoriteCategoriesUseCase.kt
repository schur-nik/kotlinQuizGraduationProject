package com.example.kotlinquizgraduationproject.ui.feature.LevelsScreen.domain.usecases

import android.util.Log
import com.example.kotlinquizgraduationproject.model.quizinfo.Category
import com.example.kotlinquizgraduationproject.repository.DBRepository
import com.example.kotlinquizgraduationproject.repository.SharedPreferencesRepository
import com.example.kotlinquizgraduationproject.ui.feature.LevelsScreen.domain.LevelsResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoadFavoriteCategoriesUseCase @Inject constructor(
    private val dbRepository: DBRepository
) {

    fun loadFavoriteCategories(): Flow<LevelsResult> {
        return flow {
            val responseFavoriteCategories = dbRepository.getAllFavoriteCategories(SharedPreferencesRepository.getUserId())
            val favoriteCategories = responseFavoriteCategories.map { entity -> Category(entity.favoriteCategory) }
            Log.e("loadFavoriteCategories", favoriteCategories.toString())
            emit(LevelsResult.FavoriteCategoriesLoaded(favoriteCategories))
        }
    }

}