package com.example.kotlinquizgraduationproject.ui.feature.LevelsScreen

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionOnScreen
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.kotlinquizgraduationproject.R
import com.example.kotlinquizgraduationproject.database.dao.UserDao
import com.example.kotlinquizgraduationproject.database.entity.LevelProgressEntity
import com.example.kotlinquizgraduationproject.database.entity.UserEntity
import com.example.kotlinquizgraduationproject.model.quizinfo.Category
import com.example.kotlinquizgraduationproject.model.usersinfo.LevelProgress
import com.example.kotlinquizgraduationproject.network.QuizApi
import com.example.kotlinquizgraduationproject.network.entity.Categories.MetadataResponse
import com.example.kotlinquizgraduationproject.network.entity.Questions.ListQuestionsResponse
import com.example.kotlinquizgraduationproject.repository.ApiRepository
import com.example.kotlinquizgraduationproject.repository.DBRepository
import com.example.kotlinquizgraduationproject.ui.fakePackage.FakeQuizRepository
import com.example.kotlinquizgraduationproject.ui.fakePackage.FakeUserDao
import com.example.kotlinquizgraduationproject.ui.feature.LevelsScreen.domain.usecases.LoadLevelProgressUseCase
import com.example.kotlinquizgraduationproject.ui.feature.LevelsScreen.domain.usecases.LoadQuestionCategoriesUseCase
import com.example.kotlinquizgraduationproject.ui.navigation.Routes
import com.example.kotlinquizgraduationproject.utils.translateCategories
import retrofit2.Response

@Preview(showBackground = true)
@Composable
fun LevelsScreenPreview() {
    val navController = rememberNavController()
    LevelsScreen(
        navHostController = navController,
        LevelsViewModel(
            LoadQuestionCategoriesUseCase(FakeQuizRepository().test()),
            LoadLevelProgressUseCase(DBRepository(FakeUserDao()))
        )
    )
}

@Composable
fun LevelsScreen(
    navHostController: NavHostController,
    viewModel: LevelsViewModel = hiltViewModel()
) {

    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT
    val state by viewModel.state.collectAsState()

    var listCategory = listOf(
        Category("science"),
        Category("sport_and_leisure"),
        Category("food_and_drink"),
        Category("music"),
        Category("general_knowledge"),
        Category("history"),
        Category("arts_and_literature"),
        Category("film_and_tv"),
        Category("society_and_culture"),
        Category("geography")
    )
    var listProgress = listOf(LevelProgress("science", "easy", 6))


    Scaffold(
        content = { padding ->
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(
                            horizontal = if (isPortrait) {
                                20.dp
                            } else {
                                50.dp
                            }, vertical = 50.dp
                        )
                        .fillMaxSize()
                ) {
                    state.run {
                        if (state.isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            )
                        }
                        if (listCategory.isNotEmpty()) {
                            BlockCategory(
                                listCategory = listCategory,
                                listProgress = listProgress,
                                translateList = translateCategories(
                                    LocalContext.current,
                                    listCategory
                                ),
                                onClick = { difficulty, category ->
                                    navHostController.navigate(
                                        Routes.QuizScreen.createRoute(difficulty, category)
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }
    )

}

@Composable
fun BlockCategory(
    listCategory: List<Category>,
    listProgress: List<LevelProgress>,
    translateList: List<Category>,
    onClick: (string1: String, string2: String) -> Unit = { _: String, _: String -> }
) {
//    var showTooltip = true
//    var tooltipMessage = "Best result:\n 6/10"
    var showTooltip by remember { mutableStateOf(false) }
    var tooltipMessage by remember { mutableStateOf("") }
    var tooltipOffset by remember { mutableStateOf(IntOffset(0, 0)) }

    val listState = rememberLazyListState()

    LaunchedEffect(listState) {
        snapshotFlow { listState.isScrollInProgress }.collect { isScrolling ->
            if (isScrolling) {
                showTooltip = false
            }
        }
    }

    if (showTooltip) {
        Popup(
            alignment = Alignment.TopStart,
            offset = tooltipOffset
        ) {
            Box(
                modifier = Modifier
                    .background(Color.White, RoundedCornerShape(8.dp))
                    .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                    .padding(0.dp)
            ) {
                IconButton(onClick = { showTooltip = false },
                    modifier = Modifier
                        .size(15.dp)
                        .align(Alignment.TopEnd)
                        .padding(top = 0.dp, end = 0.dp))
                {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                }
                Text(text = tooltipMessage, textAlign = TextAlign.Center, modifier = Modifier.padding(horizontal = 13.dp, vertical = 8.dp))
            }
        }
    }

    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxWidth(),
        contentPadding = PaddingValues(
            horizontal = 0.dp,
            vertical = 0.dp
        )
    ) {
        items(listCategory.size) { element ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp)
                    .background(getCategoryColor(listCategory[element]))
            ) {
                Image(
                    painter = painterResource(id = getCategoryImage(listCategory[element])),
                    contentDescription = null,
                    modifier = Modifier
                        .size(80.dp)
                        .align(Alignment.CenterEnd)
                        .padding(bottom = 50.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        Text(
                            translateList[element].name,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(start = 5.dp, bottom = 5.dp)
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            DifficultyButton(
                                stringResource(R.string.levelscreen_easy),
                                { onClick("easy", listCategory[element].name) },
                                Modifier
                                    .padding(1.dp)
                                    .weight(1f),
                                { offset ->
                                    tooltipMessage = "Best result:\n ${listProgress.find { it.category == listCategory[element].name && it.difficulty == "easy" }?.progress ?: 0} / 10"
                                    tooltipOffset = offset
                                    showTooltip = true
                                }
                            )
                            DifficultyButton(
                                stringResource(R.string.levelscreen_medium),
                                { onClick("medium", listCategory[element].name) },
                                Modifier
                                    .padding(1.dp)
                                    .weight(1f),
                                { offset ->
                                    tooltipMessage =  "Best result:\n ${listProgress.find { it.category == listCategory[element].name && it.difficulty == "medium" }?.progress ?: 0} / 10"
                                    tooltipOffset = offset
                                    showTooltip = true
                                }
                            )
                            DifficultyButton(
                                stringResource(R.string.levelscreen_hard),
                                { onClick("hard", listCategory[element].name) },
                                Modifier
                                    .padding(1.dp)
                                    .weight(1f),
                                { offset ->
                                    tooltipMessage =  "Best result:\n ${listProgress.find { it.category == listCategory[element].name && it.difficulty == "hard" }?.progress ?: 0} / 10"
                                    tooltipOffset = offset
                                    showTooltip = true
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DifficultyButton(
    difficulty: String,
    onClick: () -> Unit,
    modifier: Modifier,
    onImageClick: (IntOffset) -> Unit
) {
    var buttonPosition by remember { mutableStateOf(Offset(0f, 0f)) }

    Button(
        onClick = onClick,
        modifier = modifier
            .onGloballyPositioned { layoutCoordinates ->
                buttonPosition = layoutCoordinates.positionOnScreen()
            },
        contentPadding = PaddingValues(0.dp),
        colors = ButtonDefaults.buttonColors(
            colorResource(R.color.main_blue),
            Color.White
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = difficulty)
            Image(
                painter = painterResource(id = R.drawable.medal_default),
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp)
                    .pointerInput(Unit) {
                        detectTapGestures { _ ->
                            onImageClick(
                                IntOffset(
                                    buttonPosition.x.toInt() - 50,
                                    buttonPosition.y.toInt() - 15
                                )
                            )
                        }
                    }
            )
        }
    }
}

@Composable
fun getCategoryColor(category: Category): Color {
    return when (category.name) {
        "science" -> colorResource(R.color.science_theme)
        "sport_and_leisure" -> colorResource(R.color.sport_theme)
        "food_and_drink" -> colorResource(R.color.food_theme)
        "music" -> colorResource(R.color.music_theme)
        "general_knowledge" -> colorResource(R.color.general_theme)
        "history" -> colorResource(R.color.history_theme)
        "arts_and_literature" -> colorResource(R.color.literature_theme)
        "film_and_tv" -> colorResource(R.color.film_theme)
        "society_and_culture" -> colorResource(R.color.society_theme)
        "geography" -> colorResource(R.color.geography_theme)
        else -> Color.LightGray
    }
}

fun getCategoryImage(category: Category): Int {
    return when (category.name) {
        "science" -> R.drawable.science
        "sport_and_leisure" -> R.drawable.sport
        "food_and_drink" -> R.drawable.burger
        "music" -> R.drawable.music
        "general_knowledge" -> R.drawable.knowledge
        "history" -> R.drawable.history
        "arts_and_literature" -> R.drawable.literature
        "film_and_tv" -> R.drawable.film
        "society_and_culture" -> R.drawable.society
        "geography" -> R.drawable.geography
        else -> R.drawable.unknown
    }
}

//@Composable
//fun DynamicText(text: String) {
//    val textSize = when (text.length) {
//        in 0..5 -> 24.sp
//        in 6..10 -> 20.sp
//        else -> 16.sp
//    }
//}