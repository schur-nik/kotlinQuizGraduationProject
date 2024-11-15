package com.example.kotlinquizgraduationproject.ui.feature.MenuScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinquizgraduationproject.repository.ApiRepository
import com.example.kotlinquizgraduationproject.ui.feature.MenuScreen.domain.MenuAction
import com.example.kotlinquizgraduationproject.ui.feature.MenuScreen.domain.usecases.AddUserToDBUseCase
import com.example.kotlinquizgraduationproject.ui.feature.QuizScreen.domain.QuizAction
import com.example.kotlinquizgraduationproject.ui.feature.QuizScreen.domain.QuizResult
import com.example.kotlinquizgraduationproject.ui.feature.QuizScreen.domain.usecases.LoadQuestionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val addUserToDBUseCase: AddUserToDBUseCase,
) : ViewModel() {

    fun processedAction(action: MenuAction) {
        viewModelScope.launch {
            when (action) {
                is MenuAction.AddUserToDB -> addUserToDBUseCase.addUser()
            }
        }
    }

}