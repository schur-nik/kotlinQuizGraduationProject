package com.example.kotlinquizgraduationproject.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "Users",
    indices = [
        Index("u_id", unique = true)
    ]
)
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("u_id")
    val id: Int,
    @ColumnInfo("name")
    val name: String
)