package com.example.kotlinquizgraduationproject.di

import android.content.Context
import androidx.room.Room
import com.example.kotlinquizgraduationproject.R
import com.example.kotlinquizgraduationproject.database.UserInfoDB
import com.example.kotlinquizgraduationproject.database.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DBModule {

    @Provides
    @Singleton
    fun provideDB(@ApplicationContext context: Context) : UserInfoDB {
        return Room.databaseBuilder(context, UserInfoDB::class.java, name = context.getString(R.string.dbName)).build()
    }

    @Provides
    @Singleton
    fun provideUserDao(userInfoDB: UserInfoDB) : UserDao {
        return userInfoDB.getUserDao()
    }

}