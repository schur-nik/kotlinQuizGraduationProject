package com.example.kotlinquizgraduationproject

import android.app.Application
import com.example.kotlinquizgraduationproject.repository.SharedPreferencesRepository
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App: Application() {

    override fun onCreate() {
        super.onCreate()
        SharedPreferencesRepository.init(this)
    }
}