package com.example.kotlinquizgraduationproject.repository

import com.example.kotlinquizgraduationproject.database.dao.UserDao
import com.example.kotlinquizgraduationproject.database.entity.LevelProgressEntity
import com.example.kotlinquizgraduationproject.model.usersinfo.LevelProgress
import com.example.kotlinquizgraduationproject.model.usersinfo.User
import com.example.kotlinquizgraduationproject.utils.toLevelProgressEntity
import com.example.kotlinquizgraduationproject.utils.toUserEntity
import javax.inject.Inject

class DBRepository @Inject constructor(
    private val userDao: UserDao
) {

    suspend fun addUser(user: User) : Long = userDao.addUser(user.toUserEntity())

    suspend fun getUser(id: Int) = userDao.getUser(id)

    suspend fun getAllUsers() = userDao.getAllUsers()

    suspend fun addLevelProgress(levelProgress: LevelProgress, userId: Int, progressId : Int) = userDao.addLevelProgress(levelProgress.toLevelProgressEntity(progressId, userId))

    suspend fun updateLevelProgress(levelProgress: LevelProgress, userId: Int, progressId : Int) = userDao.updateLevelProgress(levelProgress.toLevelProgressEntity(progressId, userId))

    suspend fun getLevelProgress(userId: Int, category: String, difficulty: String) = userDao.getLevelProgress(userId, category, difficulty)

    suspend fun getProgress(userId: Int) : List<LevelProgressEntity> = userDao.getProgress(userId)


}