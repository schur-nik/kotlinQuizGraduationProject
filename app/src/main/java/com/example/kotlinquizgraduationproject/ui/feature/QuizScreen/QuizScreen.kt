package com.example.kotlinquizgraduationproject.ui.feature.QuizScreen

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.kotlinquizgraduationproject.model.LevelInformation

@Preview(showBackground = true)
@Composable
fun QuizScreenPreview() {
    val navController = rememberNavController()
    QuizScreen(LevelInformation("easy", "science"), navHostController = navController)
}

@Composable
fun QuizScreen(
    levelInformation: LevelInformation,
    navHostController: NavHostController,
//    viewModel: QuizViewModel = hiltViewModel()
) {

    Scaffold(content = { padding ->
        Text("Text")
    }
    )

}