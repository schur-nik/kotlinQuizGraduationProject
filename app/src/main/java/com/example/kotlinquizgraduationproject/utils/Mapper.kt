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

fun LevelProgress.toLevelProgressEntity(userId: Int): LevelProgressEntity {
    return LevelProgressEntity(0, userId, category, difficulty, progress)
}