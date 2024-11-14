package com.example.kotlinquizgraduationproject.repository

import com.example.kotlinquizgraduationproject.database.dao.UserDao
import com.example.kotlinquizgraduationproject.model.usersinfo.User
import javax.inject.Inject

class DBRepository @Inject constructor(
    private val userDao: UserDao
) {

    suspend fun addUser(user: User) = userDao.addUser(user)

    suspend fun getUser(id: Int) = userDao.getUser(id)

}