package com.example.kotlinquizgraduationproject.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.kotlinquizgraduationproject.database.entity.LevelProgressEntity
import com.example.kotlinquizgraduationproject.database.entity.UserEntity
import com.example.kotlinquizgraduationproject.model.usersinfo.User

@Dao
interface UserDao {

    @Insert
    suspend fun addUser(user: UserEntity) : Long

    @Query("SELECT * FROM Users WHERE u_id == :id LIMIT 1")
    suspend fun getUser(id: Int) : UserEntity

    @Query("SELECT * FROM Users")
    suspend fun getAllUsers() : List<UserEntity>

    @Insert
    suspend fun addLevelProgress(levelProgressEntity: LevelProgressEntity)

    @Update
    suspend fun updateLevelProgress(levelProgressEntity: LevelProgressEntity)

    @Query("SELECT * FROM LevelProgress WHERE u_id == :userId AND category == :category AND difficulty == :difficulty LIMIT 1")
    suspend fun getLevelProgress(userId: Int, category: String, difficulty : String) : LevelProgressEntity?

    @Query("SELECT * FROM LevelProgress WHERE u_id == :userId")
    suspend fun getProgress(userId: Int) : List<LevelProgressEntity>


}