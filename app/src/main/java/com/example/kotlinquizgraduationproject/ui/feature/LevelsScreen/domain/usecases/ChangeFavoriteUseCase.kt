package com.example.kotlinquizgraduationproject.ui.feature.LevelsScreen.domain.usecases

import android.util.Log
import com.example.kotlinquizgraduationproject.model.quizinfo.Category
import com.example.kotlinquizgraduationproject.repository.DBRepository
import com.example.kotlinquizgraduationproject.repository.SharedPreferencesRepository
import com.example.kotlinquizgraduationproject.ui.feature.LevelsScreen.domain.LevelsResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ChangeFavoriteUseCase @Inject constructor(
    private val dbRepository: DBRepository
) {

    fun changeFavorite(category: String, favorite: Boolean): Flow<LevelsResult> {
        return flow {
            Log.e("changeFavoriteCategories", "TRY")
            var responseFavoriteCategories = dbRepository.getAllFavoriteCategories(SharedPreferencesRepository.getUserId())
            val existingCategory = responseFavoriteCategories.find { it.favoriteCategory == category }

            if (favorite) {
                if (existingCategory != null) {
                    dbRepository.deleteFavoriteCategory(
                        Category(category),
                        SharedPreferencesRepository.getUserId(),
                        existingCategory.id
                    )
                }
            } else {
                if (existingCategory == null) {
                    dbRepository.addFavoriteCategory(
                        Category(category),
                        SharedPreferencesRepository.getUserId(),
                        0
                    )
                }
            }

            responseFavoriteCategories = dbRepository.getAllFavoriteCategories(SharedPreferencesRepository.getUserId())
            var favoriteCategories = responseFavoriteCategories.map { entity -> Category(entity.favoriteCategory) }

            Log.e("changeFavoriteCategories", favoriteCategories.toString())
            emit(LevelsResult.FavoriteCategoriesChanged(favoriteCategories))
        }
    }

}