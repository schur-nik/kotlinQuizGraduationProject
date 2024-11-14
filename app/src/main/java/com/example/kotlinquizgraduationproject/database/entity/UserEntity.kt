package com.example.kotlinquizgraduationproject.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "UsersInfo",
    indices = [
        Index("id", unique = true)
    ]
)
data class UserEntity(
    @PrimaryKey
    @ColumnInfo("r_id")
    val id: Int,
    @ColumnInfo("name")
    val name: String,
//    val favoriteCategory:
//    val progress: List<LevelProgressEntity>
)