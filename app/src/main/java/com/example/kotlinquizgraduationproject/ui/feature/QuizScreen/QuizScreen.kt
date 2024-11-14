package com.example.kotlinquizgraduationproject.ui.feature.QuizScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.runtime.LaunchedEffect
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
import com.example.kotlinquizgraduationproject.model.quizinfo.LevelInformation
import com.example.kotlinquizgraduationproject.model.quizinfo.Question
import com.example.kotlinquizgraduationproject.network.QuizApi
import com.example.kotlinquizgraduationproject.network.entity.Categories.MetadataResponse
import com.example.kotlinquizgraduationproject.network.entity.Questions.ListQuestionsResponse
import com.example.kotlinquizgraduationproject.repository.ApiRepository
import com.example.kotlinquizgraduationproject.ui.feature.QuizScreen.domain.QuizAction
import com.example.kotlinquizgraduationproject.ui.feature.QuizScreen.domain.usecases.LoadQuestionsUseCase
import com.example.kotlinquizgraduationproject.ui.navigation.Routes
import retrofit2.Response

class FakeQuizRepository {
    fun test() : ApiRepository {
        return ApiRepository(FakeQuizApi())
    }
}

class FakeQuizApi : QuizApi {
    override suspend fun getListQuestions(difficulties: String, categories: String): Response<ListQuestionsResponse> {
        return Response.success(ListQuestionsResponse())
    }

    override suspend fun getListOfQuestionCategories(): Response<MetadataResponse> {
        return TODO("Provide the return value")
    }
}

@Preview(showBackground = true)
@Composable
fun QuizScreenPreview() {
    val navController = rememberNavController()
    QuizScreen(
        LevelInformation("easy", "science"),
        navHostController = navController,
        viewModel = QuizViewModel(LoadQuestionsUseCase(FakeQuizRepository().test())))
}

@Composable
fun QuizScreen(
    levelInformation: LevelInformation,
    navHostController: NavHostController,
    viewModel: QuizViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsState()
    LaunchedEffect(levelInformation) {
        viewModel.processedAction(QuizAction.Init(levelInformation))
    }

    Scaffold(
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
//                Text(text = "category=" + levelInformation.category)
//                Text(text = "category=" + levelInformation.difficulty)
                state.run {
                    val currentQuestion = Question("1", listOf("1", "2", "3", "4"), "Questiuon is here")
//                    val userAnswer = "1"
                    val questionList = listOf(Question("1", listOf("1", "2", "3", "4"), "Questiuon is here"))
//                    Text(text = "currentNumber=" + state.currentNumber)
//                    Text(text = "size=" + state.questionList.size)
//                    Text(text = "userAnswer=" + state.userAnswer)
//                    Text(text = "correctAnswer=" + state.currentQuestion?.correctAnswer)
//                    Text(text = "endQuiz=" + state.endQuiz)
                    if (state.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .fillMaxSize()
                        )
                    }
                    if (endQuiz) {
                        Column(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(top = 160.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = "QUIZ IS END")
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(onClick = { navHostController.navigate(Routes.LevelsScreen.route) }) {
                                Text(text = "TO MENU")
                            }
                        }
                    }
                    if (currentQuestion != null) {
                        Column(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(top = 100.dp)
                                .padding(15.dp)
                        )
                        {
                            Text(text = currentQuestion.question, fontSize = 20.sp, minLines = 5)
                            LazyVerticalGrid(
                                modifier = Modifier.padding(top = 24.dp),
                                columns = GridCells.Fixed(2)
                            ) {
                                currentQuestion.allAnswers.size.let {
                                    items(it) { answer ->
                                        QuestionBox(
                                            answer = currentQuestion.allAnswers[answer],
                                            correctAnswer = currentQuestion.correctAnswer,
                                            userAnswer = userAnswer,
                                            onClick = {
                                                if (userAnswer == null) {
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
                    }
                    if (questionList.isEmpty()) {
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