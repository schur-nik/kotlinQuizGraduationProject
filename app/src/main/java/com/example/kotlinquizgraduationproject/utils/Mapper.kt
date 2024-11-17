package com.example.kotlinquizgraduationproject.utils

import com.example.kotlinquizgraduationproject.database.entity.LevelProgressEntity
import com.example.kotlinquizgraduationproject.database.entity.UserEntity
import com.example.kotlinquizgraduationproject.model.usersinfo.LevelProgress
import com.example.kotlinquizgraduationproject.model.usersinfo.User

fun User.toUserEntity(): UserEntity {
    return UserEntity(
        0,
        name)
}

fun LevelProgress.toLevelProgressEntity(progressId: Int, userId: Int): LevelProgressEntity {
    return LevelProgressEntity(progressId, userId, category, difficulty, progress)
}