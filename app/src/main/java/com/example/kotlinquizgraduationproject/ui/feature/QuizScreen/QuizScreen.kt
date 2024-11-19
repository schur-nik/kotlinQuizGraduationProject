package com.example.kotlinquizgraduationproject.ui.feature.QuizScreen

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ShareCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.kotlinquizgraduationproject.R
import com.example.kotlinquizgraduationproject.model.quizinfo.Category
import com.example.kotlinquizgraduationproject.model.quizinfo.LevelInformation
import com.example.kotlinquizgraduationproject.model.quizinfo.Question
import com.example.kotlinquizgraduationproject.repository.DBRepository
import com.example.kotlinquizgraduationproject.ui.fakePackage.FakeQuizRepository
import com.example.kotlinquizgraduationproject.ui.fakePackage.FakeUserDao
import com.example.kotlinquizgraduationproject.ui.feature.QuizScreen.domain.QuizAction
import com.example.kotlinquizgraduationproject.ui.feature.QuizScreen.domain.QuizState
import com.example.kotlinquizgraduationproject.ui.feature.QuizScreen.domain.usecases.AnswerQuestionUseCase
import com.example.kotlinquizgraduationproject.ui.feature.QuizScreen.domain.usecases.FinishQuizUseCase
import com.example.kotlinquizgraduationproject.ui.feature.QuizScreen.domain.usecases.LoadQuestionsUseCase
import com.example.kotlinquizgraduationproject.ui.navigation.Routes
import com.example.kotlinquizgraduationproject.utils.getCategoryColor
import com.example.kotlinquizgraduationproject.utils.translateCategories

@Preview(showBackground = true)
@Composable
fun QuizScreenPreview() {
    val navController = rememberNavController()
    QuizScreen(
        LevelInformation("easy", "sport_and_leisure"),
        navHostController = navController,
        viewModel = QuizViewModel(
            LoadQuestionsUseCase(FakeQuizRepository().test()),
            AnswerQuestionUseCase(),
            FinishQuizUseCase(DBRepository(FakeUserDao()))
        )
    )
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
                state.run {
//                    var currentQuestion: Question? =
//                        Question(
//                            "1",
//                            listOf(
//                                "ANSWER1",
//                                "ANSWER2",
//                                "ANSWER3",
//                                "123456789123456789123456789123456789"
//                            ),
//                            "Questiuon is hereQuestiuon is hereQuestiuon is hereQuestiuon is hereQuestiuon is hereQuestiuon is hereQuestiuon is hereQuestiuon is hereQuestiuon is hereQuestiuon is hereQuestiuon is hereQuestiuon is hereQuestiuon is hereQuestiuon is hereQuestiuon is here"
//                        )
//                    val questionCount = 0
//                    val endQuiz = true
//                    currentQuestion = null

                    if (isLoading) {
                        CircularProgressIndicator(modifier = Modifier.fillMaxSize())
                    } else {
                        currentQuestion?.let { currentQuestion ->
                            QuizContent(
                                levelInformation = levelInformation,
                                currentQuestion = currentQuestion,
                                state = state,
                                finishClick = { viewModel.processedAction(QuizAction.FinishQuiz) },
                                nextClick = { viewModel.processedAction(QuizAction.NextQuestion) },
                                answerClick = { indexAnswer ->
                                    if (userAnswer == null) viewModel.processedAction(
                                        QuizAction.AnswerQuestion(currentQuestion.allAnswers[indexAnswer])
                                    )
                                }
                            )
                        } ?: run {
                            if (questionCount == 0 && !endQuiz) {
                                Column(
                                    modifier = Modifier
                                        .align(Alignment.CenterHorizontally)
                                        .padding(top = 360.dp),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(text = "NO_DATA_FOUND")
                                    Spacer(modifier = Modifier.padding(10.dp))
                                    Button(onClick = { navHostController.navigate(Routes.LevelsScreen.route) }) {
                                        Text(text = "BACK TO MENU")
                                    }
                                }
                            } else if (endQuiz) {
                                EndQuizScreen(navHostController, levelInformation, state, LocalContext.current)
                            }
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun EndQuizScreen(
    navHostController: NavHostController,
    levelInformation: LevelInformation,
    state: QuizState,
    current: Context
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .width(300.dp)
                .height(220.dp)
                .background(Color(0xFFADD8E6))
                .padding(16.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.quizscreen_results),
                    fontSize = 30.sp,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(bottom = 16.dp)
                )
                Text(
                    text = stringResource(
                        R.string.quizscreen_theme, translateCategories(
                            LocalContext.current,
                            listOf(Category(levelInformation.category))
                        ).first().name
                    ),
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = stringResource(
                        R.string.quizscreen_difficulty,
                        levelInformation.difficulty
                    ),
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = stringResource(
                        R.string.quizscreen_correct_answers,
                        state.correctAnswersCount
                    ),
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Button(
                        onClick = { navHostController.navigate(Routes.LevelsScreen.route) {
                                popUpTo(navHostController.graph.startDestinationId) { inclusive = false }
                            }},
                        modifier = Modifier
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        colorResource(R.color.main_blue),
                                        Color.Black
                                    ),
                                    startY = 0f,
                                    endY = 260f
                                ),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .align(Alignment.Center),
                        colors = ButtonDefaults.buttonColors(
                            Color.Transparent,
                            Color.White
                        ),
                    )
                    {
                        Text(text = stringResource(R.string.quizscreen_finish))
                    }

                    Box(
                        modifier = Modifier
                            .size(30.dp)
                            .border(2.dp, Color.Black, RoundedCornerShape(8.dp))
                            .padding(4.dp)
                            .align(Alignment.CenterEnd)
                    ) {
                        IconButton(
                            onClick = {
                                shareResults(current, "RESULTS: FOR THEME = "+levelInformation.category
                                        +" AND DIFFICULTY = "+levelInformation.difficulty+": "
                                        +state.correctAnswersCount+"/10. Congratulation!")
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Send,
                                contentDescription = null,
                                tint = Color.Blue
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun shareResults(context: Context, quizResults: String) {
    val shareIntent = ShareCompat.IntentBuilder(context)
        .setType("text/plain")
        .setChooserTitle(context.getString(R.string.quizscreen_share_results))
        .setText(quizResults)
        .createChooserIntent()

    context.startActivity(shareIntent)
}

@Composable
fun QuizContent(
    levelInformation: LevelInformation,
    currentQuestion: Question,
    state: QuizState,
    finishClick: () -> Unit,
    nextClick: () -> Unit,
    answerClick: (index: Int) -> Unit = { _: Int -> },
) {
    Column(modifier = Modifier.padding(top = 100.dp, start = 15.dp, end = 15.dp)) {
        Text(
            text = stringResource(
                R.string.quizscreen_theme, translateCategories(
                    LocalContext.current,
                    listOf(Category(levelInformation.category))
                ).first().name
            ),
            modifier = Modifier.padding(horizontal = 5.dp)
        )
        Text(
            text = stringResource(R.string.quizscreen_question, state.currentNumber.inc()),
            fontSize = 30.sp,
            modifier = Modifier
                .padding(horizontal = 5.dp)
                .padding(bottom = 5.dp)
        )
        QuestionBox(levelInformation, currentQuestion, state.userAnswer, answerClick)
        ProgressAndNavigation(state, finishClick, nextClick)
    }
}

@Composable
fun ProgressAndNavigation(state: QuizState, finishClick: () -> Unit, nextClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Answer: ${state.correctAnswersCount}/${state.questionCount}",
            modifier = Modifier.padding(end = 5.dp)
        )
        Button(
            onClick = {
                if (state.currentNumber + 1 >= state.questionCount) {
                    finishClick()
                } else {
                    nextClick()
                }
            },
            modifier = Modifier
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(colorResource(R.color.main_blue), Color.Black),
                        startY = 0f,
                        endY = 260f
                    ),
                    shape = RoundedCornerShape(8.dp)
                )
                .height(40.dp),
            colors = ButtonDefaults.buttonColors(
                Color.Transparent,
                Color.White
            ),
        ) {
            Text(text = if (state.currentNumber + 1 >= state.questionCount) "END" else "NEXT")
        }
    }
}

@Composable
fun QuestionBox(
    levelInformation: LevelInformation,
    currentQuestion: Question,
    userAnswer: String?,
    answerClick: (Int) -> Unit
) {
    LazyColumn {
        item {
            Box(
                modifier = Modifier
                    .background(
                        getCategoryColor(Category(levelInformation.category)),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(10.dp)
                    .height(140.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = currentQuestion.question,
                    fontSize = 20.sp,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
    LazyVerticalGrid(
        modifier = Modifier.padding(top = 24.dp),
        columns = GridCells.Fixed(2)
    ) {
        items(currentQuestion.allAnswers.size) { index ->
            AnswerBox(
                answer = currentQuestion.allAnswers[index],
                correctAnswer = currentQuestion.correctAnswer,
                userAnswer = userAnswer,
                onClick = { answerClick(index) }
            )
        }
    }
}

@Composable
fun AnswerBox(answer: String, correctAnswer: String?, userAnswer: String?, onClick: () -> Unit) {
    val baseModifier = Modifier
        .fillMaxWidth()
        .height(100.dp)
        .padding(8.dp)
        .background(Color.Black, shape = RoundedCornerShape(8.dp))

    val gradientColors = listOf(
        when {
            userAnswer == null -> colorResource(R.color.main_blue)
            answer == userAnswer -> if (userAnswer == correctAnswer) Color.Green else Color.Red
            else -> if (answer == correctAnswer) Color.Green else Color.Red
        },
        Color.Black
    )

    val modifier = baseModifier
        .background(brush = Brush.verticalGradient(colors = gradientColors))
        .clickable { onClick() }

    AnswerText(answer, modifier)
}

@Composable
fun AnswerText(title: String, modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Text(
            text = title,
            textAlign = TextAlign.Center,
            fontSize = DynamicText(title),
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 3.dp),
            color = Color.LightGray
        )
    }
}

@Composable
fun DynamicText(text: String): TextUnit {
    return when (text.length) {
        in 0..24 -> 20.sp
        in 24..35 -> 18.sp
        else -> 16.sp
    }
}