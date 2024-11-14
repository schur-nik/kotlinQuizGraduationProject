package com.example.kotlinquizgraduationproject.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.kotlinquizgraduationproject.database.entity.UserEntity
import com.example.kotlinquizgraduationproject.model.usersinfo.User

@Dao
interface UserDao {

    @Insert
    suspend fun addUser(user: User)

    @Query("SELECT * FROM UsersInfo WHERE r_id == :id LIMIT 1")
    suspend fun getUser(id: Int) : UserEntity

//    @Query("SELECT * FROM Users")
//    fun getUsers() : List<UserEntity>

}