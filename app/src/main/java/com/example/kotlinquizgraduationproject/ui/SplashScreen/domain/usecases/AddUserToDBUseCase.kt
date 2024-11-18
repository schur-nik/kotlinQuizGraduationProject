package com.example.kotlinquizgraduationproject.ui.SplashScreen.domain.usecases

import android.util.Log
import com.example.kotlinquizgraduationproject.model.quizinfo.Category
import com.example.kotlinquizgraduationproject.model.usersinfo.User
import com.example.kotlinquizgraduationproject.repository.DBRepository
import com.example.kotlinquizgraduationproject.repository.SharedPreferencesRepository
import com.example.kotlinquizgraduationproject.utils.toFavoriteCategoryEntity
import javax.inject.Inject
import javax.inject.Named

class AddUserToDBUseCase @Inject constructor(
    private val dbRepository: DBRepository
) {

    suspend fun addUser(name: String, favoriteList: List<Category>) {
        SharedPreferencesRepository.setUserId(dbRepository.addUser(User(name, listOf(), listOf())).toInt())
        favoriteList.forEach { category ->
            dbRepository.addFavoriteCategory(category, SharedPreferencesRepository.getUserId(), 0)
        }
        Log.e("USER_ID", "USER_ID"+SharedPreferencesRepository.getUserId().toString())
    }

}
