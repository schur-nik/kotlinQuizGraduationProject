package com.example.kotlinquizgraduationproject.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.kotlinquizgraduationproject.ui.navigation.AppGraph
import com.example.kotlinquizgraduationproject.ui.theme.KotlinQuizGraduationProjectTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KotlinQuizGraduationProjectTheme {
                AppGraph(navController = rememberNavController())
            }
        }
    }
}