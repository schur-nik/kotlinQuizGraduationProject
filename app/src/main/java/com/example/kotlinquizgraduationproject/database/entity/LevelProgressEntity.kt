package com.example.kotlinquizgraduationproject.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "LevelProgress",
    foreignKeys = [ForeignKey(
        entity = UserEntity::class,
        parentColumns = ["u_id"],
        childColumns = ["u_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class LevelProgressEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("lv_id")
    var id: Int,
    @ColumnInfo("u_id")
    var userId: Int,
    @ColumnInfo("category")
    val category: String,
    @ColumnInfo("difficulty")
    val difficulty: String,
    @ColumnInfo("progress")
    val progress: Int
)