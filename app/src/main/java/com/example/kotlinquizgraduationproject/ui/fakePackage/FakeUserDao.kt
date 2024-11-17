package com.example.kotlinquizgraduationproject.ui.fakePackage

import com.example.kotlinquizgraduationproject.database.dao.UserDao
import com.example.kotlinquizgraduationproject.database.entity.LevelProgressEntity
import com.example.kotlinquizgraduationproject.database.entity.UserEntity

class FakeUserDao : UserDao {
    override suspend fun addUser(user: UserEntity): Long {
        TODO("Not yet implemented")
    }

    override suspend fun getUser(id: Int): UserEntity {
        TODO("Not yet implemented")
    }

    override suspend fun getAllUsers(): List<UserEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun addLevelProgress(levelProgressEntity: LevelProgressEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun updateLevelProgress(levelProgressEntity: LevelProgressEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun getLevelProgress(
        userId: Int,
        category: String,
        difficulty: String
    ): LevelProgressEntity? {
        TODO("Not yet implemented")
    }

    override suspend fun getProgress(u_id: Int): List<LevelProgressEntity> {
        TODO("Not yet implemented")
    }

}