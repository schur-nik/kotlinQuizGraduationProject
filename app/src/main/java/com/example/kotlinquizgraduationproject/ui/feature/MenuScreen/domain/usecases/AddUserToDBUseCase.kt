package com.example.kotlinquizgraduationproject.ui.feature.MenuScreen.domain.usecases

import android.util.Log
import com.example.kotlinquizgraduationproject.model.quizinfo.Category
import com.example.kotlinquizgraduationproject.model.usersinfo.User
import com.example.kotlinquizgraduationproject.repository.DBRepository
import com.example.kotlinquizgraduationproject.repository.SharedPreferencesRepository
import javax.inject.Inject

class AddUserToDBUseCase @Inject constructor(
    private val dbRepository: DBRepository
) {

    suspend fun addUser() {
        SharedPreferencesRepository.setUserId(dbRepository.addUser(User("Nikita", listOf(Category("science")), listOf())).toInt())
        Log.e("USER_ID", "USER_ID"+SharedPreferencesRepository.getUserId().toString())
    }

}
