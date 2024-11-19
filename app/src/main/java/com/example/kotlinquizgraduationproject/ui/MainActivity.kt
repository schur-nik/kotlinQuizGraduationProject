package com.example.kotlinquizgraduationproject.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.kotlinquizgraduationproject.repository.SharedPreferencesRepository
import com.example.kotlinquizgraduationproject.ui.navigation.AppGraph
import com.example.kotlinquizgraduationproject.ui.theme.KotlinQuizGraduationProjectTheme
import com.example.kotlinquizgraduationproject.utils.updateLocale
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (SharedPreferencesRepository.isFirstLaunch())
            SharedPreferencesRepository.setLanguage(Locale.getDefault().language)

        val language = SharedPreferencesRepository.getLanguage()
        updateLocale(this, language)

        enableEdgeToEdge()
        setContent {
            KotlinQuizGraduationProjectTheme {
                AppGraph(navController = rememberNavController())
            }
        }
    }
}