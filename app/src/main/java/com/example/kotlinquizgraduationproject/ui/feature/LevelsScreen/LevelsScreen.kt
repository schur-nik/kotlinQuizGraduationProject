package com.example.kotlinquizgraduationproject.ui.feature.LevelsScreen

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
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
import com.example.kotlinquizgraduationproject.model.quizinfo.Category
import com.example.kotlinquizgraduationproject.model.usersinfo.LevelProgress
import com.example.kotlinquizgraduationproject.repository.DBRepository
import com.example.kotlinquizgraduationproject.ui.fakePackage.FakeQuizRepository
import com.example.kotlinquizgraduationproject.ui.fakePackage.FakeUserDao
import com.example.kotlinquizgraduationproject.ui.feature.LevelsScreen.domain.LevelsAction
import com.example.kotlinquizgraduationproject.ui.feature.LevelsScreen.domain.usecases.ChangeFavoriteUseCase
import com.example.kotlinquizgraduationproject.ui.feature.LevelsScreen.domain.usecases.LoadFavoriteCategoriesUseCase
import com.example.kotlinquizgraduationproject.ui.feature.LevelsScreen.domain.usecases.LoadLevelProgressUseCase
import com.example.kotlinquizgraduationproject.ui.feature.LevelsScreen.domain.usecases.LoadQuestionCategoriesUseCase
import com.example.kotlinquizgraduationproject.ui.navigation.Routes
import com.example.kotlinquizgraduationproject.utils.getCategoryColor
import com.example.kotlinquizgraduationproject.utils.getCategoryImage
import com.example.kotlinquizgraduationproject.utils.translateCategories

@Preview(showBackground = true)
@Composable
fun LevelsScreenPreview() {
    val navController = rememberNavController()
    LevelsScreen(
        navHostController = navController,
        LevelsViewModel(
            LoadQuestionCategoriesUseCase(FakeQuizRepository().test()),
            LoadLevelProgressUseCase(DBRepository(FakeUserDao())),
            LoadFavoriteCategoriesUseCase(DBRepository(FakeUserDao())),
            ChangeFavoriteUseCase(DBRepository(FakeUserDao()))
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

    val listState = rememberLazyListState()

//    var listCategory = listOf(
//        Category("science"),
//        Category("sport_and_leisure"),
//        Category("food_and_drink"),
//        Category("music"),
//        Category("general_knowledge"),
//        Category("history"),
//        Category("arts_and_literature"),
//        Category("film_and_tv"),
//        Category("society_and_culture"),
//        Category("geography")
//    )
//    var listProgress = listOf(LevelProgress("science", "easy", 8))
//    var listFavorites = listOf(
//        Category("science"),
//        Category("sport_and_leisure"),
//        Category("food_and_drink")
//    )

    Scaffold(
        content = { padding ->
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(horizontal = if (isPortrait) 20.dp else 50.dp, vertical = 50.dp)
                        .fillMaxSize()
                ) {
                    state.run {
                        item {
                            if (isLoading) {
                                Column(modifier = Modifier.fillMaxWidth()) {
                                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                                }
                            }
                        }
                        if (listCategory.isNotEmpty()) {
                            item {
                                BlockCategory(
                                    listCategory = listFavorites,
                                    listProgress = listProgress,
                                    translateList = translateCategories(LocalContext.current, listFavorites),
                                    onClick = { difficulty, category ->
                                        navHostController.navigate(Routes.QuizScreen.createRoute(difficulty, category))
                                    },
                                    favorite = true,
                                    onClickFavorite = { favorite, category -> viewModel.processedAction(LevelsAction.ChangeFavorite(favorite, category)) },
                                    listState = listState
                                )
                            }
                            item { Spacer(modifier = Modifier.padding(10.dp)) }
                            item {
                                BlockCategory(
                                    listCategory = listCategory - listFavorites.toSet(),
                                    listProgress = listProgress,
                                    translateList = translateCategories(LocalContext.current, listCategory - listFavorites.toSet()),
                                    onClick = { difficulty, category ->
                                        navHostController.navigate(Routes.QuizScreen.createRoute(difficulty, category))
                                    },
                                    favorite = false,
                                    onClickFavorite = { favorite, category -> viewModel.processedAction(LevelsAction.ChangeFavorite(favorite, category)) },
                                    listState = listState
                                )
                            }
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
    onClick: (string1: String, string2: String) -> Unit = { _: String, _: String -> },
    favorite: Boolean,
    onClickFavorite: (string: String, boolean: Boolean) -> Unit = { _: String, _: Boolean -> },
    listState: LazyListState
) {
//    var showTooltip = true
//    var tooltipMessage = "Best result:\n 6/10"
    var showTooltip by remember { mutableStateOf(false) }
    var tooltipMessage by remember { mutableStateOf("") }
    var tooltipOffset by remember { mutableStateOf(IntOffset(0, 0)) }
    var expandedStates by remember { mutableStateOf(false) }

    LaunchedEffect(listState) {
        snapshotFlow { listState.isScrollInProgress }.collect { isScrolling ->
            if (isScrolling) {
                showTooltip = false
            }
        }
    }

    if (showTooltip) {
        Tooltip(tooltipMessage, tooltipOffset) { showTooltip = false }
    }

    if (!favorite) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Row(modifier = Modifier
                .height(24.dp)
                .border(1.dp, Color.Black)
                .padding(start = 3.dp)
                .clickable { expandedStates = !expandedStates }) {
                Text(text = stringResource(R.string.levelscreen_unfavorite_topics), fontSize = 15.sp, modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f))
                Icon(imageVector = if (expandedStates) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown, contentDescription = null)
            }
        }
    }
    else {
        expandedStates = true
        Text(text = stringResource(R.string.levelscreen_favorite_topics), fontSize = 15.sp)
    }

    if (expandedStates) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            for (index in listCategory.indices) {
                CategoryItem(
                    category = listCategory[index],
                    translatedName = translateList[index].name,
                    progress = listProgress,
                    onClick = onClick,
                    showTooltip = { message, offset ->
                        tooltipMessage = message
                        tooltipOffset = offset
                        showTooltip = true
                    },
                    favorite = favorite,
                    onClickFavorite = onClickFavorite
                )
            }
        }
    }
}

@Composable
fun Tooltip(message: String, offset: IntOffset, onClose: () -> Unit) {
    Popup(alignment = Alignment.TopStart, offset = offset) {
        Box(
            modifier = Modifier
                .background(Color.White, RoundedCornerShape(8.dp))
                .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                .padding(0.dp)
        ) {
            IconButton(onClick = onClose, modifier = Modifier
                .size(15.dp)
                .align(Alignment.TopEnd)
                .padding(0.dp)) {
                Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
            }
            Text(text = message, textAlign = TextAlign.Center, modifier = Modifier.padding(horizontal = 13.dp, vertical = 8.dp))
        }
    }
}

@Composable
fun CategoryItem(
    category: Category,
    translatedName: String,
    progress: List<LevelProgress>,
    onClick: (String, String) -> Unit,
    showTooltip: (String, IntOffset) -> Unit,
    favorite: Boolean,
    onClickFavorite: (String, Boolean) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
            .background(getCategoryColor(category))
    ) {
        Image(
            painter = painterResource(id = getCategoryImage(category)),
            contentDescription = null,
            modifier = Modifier
                .size(80.dp)
                .align(Alignment.CenterEnd)
                .padding(bottom = 50.dp)
        )
        Row(modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)) {
            Column(modifier = Modifier.weight(1f)) {
                Text(translatedName, fontSize = 24.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 5.dp, bottom = 5.dp))
                DifficultyButtons(category, progress, onClick, showTooltip)
            }
        }
        Button(
            onClick = { Log.e("TRY BUTTON" ,"FOR "+category.name+" and favoris = "+favorite.toString()) ; onClickFavorite(category.name, favorite)},
            modifier = Modifier
                .align(Alignment.TopEnd)
                .size(25.dp)
                .offset(x = 5.dp, y = (-5).dp),
            contentPadding = PaddingValues(0.dp),
            colors = ButtonDefaults.buttonColors(Color.White, Color.White),
            shape = RoundedCornerShape(8.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.star_default),
                contentDescription = null,
                modifier = Modifier
                    .size(25.dp),
                colorFilter = ColorFilter.lighting(Color.Black, if (favorite) Color.Red else Color.Black)
            )
        }
    }
}

@Composable
fun DifficultyButtons(
    category: Category,
    progress: List<LevelProgress>,
    onClick: (String, String) -> Unit,
    showTooltip: (String, IntOffset) -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        listOf("easy", "medium", "hard").forEach { difficulty ->
            DifficultyButton(
                difficulty = stringResource(id = when (difficulty) {
                    "easy" -> R.string.levelscreen_easy
                    "medium" -> R.string.levelscreen_medium
                    "hard" -> R.string.levelscreen_hard
                    else -> R.string.levelscreen_easy
                }),
                onClick = { onClick(difficulty, category.name) },
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 6.dp),
                onImageClick = { offset ->
                    val bestResult = progress.find { it.category == category.name && it.difficulty == difficulty }?.progress ?: 0
                    showTooltip("Best result:\n $bestResult / 10", offset)
                },
                bestProgress = progress.find { it.category == category.name && it.difficulty == difficulty }?.progress ?: 0
            )
        }
    }
}

@Composable
fun DifficultyButton(
    difficulty: String,
    onClick: () -> Unit,
    modifier: Modifier,
    onImageClick: (IntOffset) -> Unit,
    bestProgress: Int
) {
    var buttonPosition by remember { mutableStateOf(Offset(0f, 0f)) }

    Button(
        onClick = onClick,
        modifier = modifier
            .onGloballyPositioned { layoutCoordinates ->
                buttonPosition = layoutCoordinates.positionOnScreen()
            }
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
        ,
        contentPadding = PaddingValues(0.dp),
        colors = ButtonDefaults.buttonColors(
            Color.Transparent,
            Color.White
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
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
                        },
                    colorFilter = ColorFilter.lighting(
                        Color.Black,
                        when (bestProgress) {
                            8 -> colorResource(R.color.bronze)
                            9 -> colorResource(R.color.silver)
                            10 -> colorResource(R.color.gold)
                            else -> Color.Black
                        }
                    )
                )
            }
        }
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