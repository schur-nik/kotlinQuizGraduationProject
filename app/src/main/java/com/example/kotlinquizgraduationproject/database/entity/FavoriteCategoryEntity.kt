package com.example.kotlinquizgraduationproject.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "FavoriteCategory",
    foreignKeys = [ForeignKey(
        entity = UserEntity::class,
        parentColumns = ["u_id"],
        childColumns = ["u_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class FavoriteCategoryEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("f_id")
    var id: Int,
    @ColumnInfo("u_id")
    var userId: Int,
    @ColumnInfo("favorite_category")
    val favoriteCategory: String
)