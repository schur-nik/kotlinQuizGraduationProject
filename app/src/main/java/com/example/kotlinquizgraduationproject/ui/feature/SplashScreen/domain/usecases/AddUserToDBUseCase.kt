package com.example.kotlinquizgraduationproject.ui.feature.SplashScreen.domain.usecases

import com.example.kotlinquizgraduationproject.model.quizinfo.Category
import com.example.kotlinquizgraduationproject.model.usersinfo.User
import com.example.kotlinquizgraduationproject.repository.DBRepository
import com.example.kotlinquizgraduationproject.repository.SharedPreferencesRepository
import javax.inject.Inject

class AddUserToDBUseCase @Inject constructor(
    private val dbRepository: DBRepository
) {

    suspend fun addUser(name: String, favoriteList: List<Category>) {
        SharedPreferencesRepository.setUserId(dbRepository.addUser(User(name, listOf(), listOf())).toInt())
        favoriteList.forEach { category ->
            dbRepository.addFavoriteCategory(category, SharedPreferencesRepository.getUserId(), 0)
        }
    }

}
