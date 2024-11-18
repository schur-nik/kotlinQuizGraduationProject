package com.example.kotlinquizgraduationproject.ui.SplashScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinquizgraduationproject.ui.SplashScreen.domain.SplashAction
import com.example.kotlinquizgraduationproject.ui.SplashScreen.domain.usecases.AddUserToDBUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val addUserToDBUseCase: AddUserToDBUseCase,
) : ViewModel() {

    fun processedAction(action: SplashAction) {
        viewModelScope.launch {
            when (action) {
                is SplashAction.AddUserToDB -> addUserToDBUseCase.addUser(action.name, action.listFavorite)
            }
        }
    }

}