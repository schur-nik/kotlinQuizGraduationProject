package com.example.kotlinquizgraduationproject.ui.feature.QuizScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.kotlinquizgraduationproject.model.LevelInformation
import com.example.kotlinquizgraduationproject.ui.feature.QuizScreen.domain.QuizAction
import com.example.kotlinquizgraduationproject.ui.navigation.Routes

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
    viewModel: QuizViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsState()
    viewModel.processedAction(QuizAction.Init(levelInformation))

    Scaffold(
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                Text(text = "category=" + levelInformation.category)
                Text(text = "category=" + levelInformation.difficulty)
                state.run {
                    Text(text = "currentNumber=" + state.currentNumber)
                    Text(text = "size=" + state.questionList.size)
                    Text(text = "userAnswer=" + state.userAnswer)
                    Text(text = "correctAnswer=" + state.currentQuestion?.correctAnswer)
                    Text(text = "endQuiz=" + state.endQuiz)
                    if (state.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .fillMaxSize()
                        )
                    }
                    if (endQuiz) {
                        Text(text = "QUIZ IS END")
                        Button(onClick = { navHostController.navigate(Routes.LevelsScreen.route) }) {
                            Text(text = "TO MENU")
                        }
                    }
                    if (questionList.isNotEmpty() && !endQuiz && currentQuestion != null) {
                        Column(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(top = 100.dp)
                                .padding(15.dp)
                        )
                        {
                            Text(text = currentQuestion.question, fontSize = 20.sp)
                            LazyVerticalGrid(
                                modifier = Modifier.padding(top = 24.dp),
                                columns = GridCells.Fixed(2)
                            ) {
                                currentQuestion.allAnswers.size.let {
                                    items(it) { answer ->
                                        QuestionBox(
                                            answer = currentQuestion.allAnswers[answer],
                                            correctAnswer = currentQuestion.correctAnswer,
                                            userAnswer = state.userAnswer,
                                            onClick = {
                                                if (state.userAnswer == null) {
                                                    viewModel.processedAction(
                                                        QuizAction.AnswerQuestion(
                                                            currentQuestion.allAnswers[answer]
                                                        )
                                                    )
                                                }
                                            }
                                        )
                                    }
                                }
                            }
                            Button(
                                modifier = Modifier.align(Alignment.End),
                                onClick = { viewModel.processedAction(QuizAction.NextQuestion) }) {
                                Text(text = "NEXT")
                            }
                        }
                    } else {
                        Text(text = "NO_DATA_FOUND")
                    }
                }
            }
        }
    )

}

@Composable
fun QuestionBox(answer: String, correctAnswer: String?, userAnswer: String?, onClick: () -> Unit) {
    val modifier = when {
        userAnswer == null -> {
            Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(8.dp)
                .background(Color.Blue)
                .clickable {
                    onClick()
                }
        }

        answer == userAnswer -> {
            Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(8.dp)
                .background(
                    if (userAnswer == correctAnswer) {
                        Color.Green
                    } else {
                        Color.Red
                    }
                )
                .clickable {
                    onClick()
                }
        }

        else -> {
            Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(8.dp)
                .background(
                    if (answer == correctAnswer) {
                        Color.Green
                    } else {
                        Color.Red
                    }
                )
        }
    }
    AnswerBox(
        answer, modifier = modifier
    )
}

@Composable
fun AnswerBox(title: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier

    ) {
        Text(
            text = title,
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}