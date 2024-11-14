package com.example.kotlinquizgraduationproject.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.kotlinquizgraduationproject.database.dao.UserDao
import com.example.kotlinquizgraduationproject.database.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class UserInfoDB : RoomDatabase() {

    abstract fun getUserDao() : UserDao

}