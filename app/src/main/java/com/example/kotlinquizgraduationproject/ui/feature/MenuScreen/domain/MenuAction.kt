package com.example.kotlinquizgraduationproject.ui.feature.MenuScreen.domain

import com.example.kotlinquizgraduationproject.model.quizinfo.LevelInformation

sealed class MenuAction {

    data object AddUserToDB : MenuAction()

}