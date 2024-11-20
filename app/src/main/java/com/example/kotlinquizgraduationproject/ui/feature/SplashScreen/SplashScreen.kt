package com.example.kotlinquizgraduationproject.ui.feature.SplashScreen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.kotlinquizgraduationproject.R
import com.example.kotlinquizgraduationproject.model.quizinfo.Category
import com.example.kotlinquizgraduationproject.repository.DBRepository
import com.example.kotlinquizgraduationproject.repository.SharedPreferencesRepository
import com.example.kotlinquizgraduationproject.ui.feature.SplashScreen.domain.SplashAction
import com.example.kotlinquizgraduationproject.ui.fakePackage.FakeUserDao
import com.example.kotlinquizgraduationproject.ui.feature.SplashScreen.domain.usecases.AddUserToDBUseCase
import com.example.kotlinquizgraduationproject.ui.navigation.Routes

@Preview(showBackground = true)
@Composable
fun SplashScreen() {
    val navController = rememberNavController()
    SplashScreen(
        navHostController = navController,
        viewModel = SplashViewModel(AddUserToDBUseCase(DBRepository(FakeUserDao())))
    )
}

@Composable
fun SplashScreen(
    navHostController: NavHostController,
    viewModel: SplashViewModel = hiltViewModel()
) {

    var name by remember { mutableStateOf("") }

    Scaffold(
        content = { padding ->
            Box(
                modifier = Modifier.fillMaxSize(),
            ) {
                Image(
                    painter = painterResource(id = R.drawable.main_background),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                if (isSystemInDarkTheme()) {
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .background(Color.Black.copy(alpha = 0.6f))
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 35.dp)
                        .padding(top = 250.dp)
                        .padding(bottom = 300.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .background(
                                if (isSystemInDarkTheme()) {
                                    Color.Black.copy(alpha = 0.6f)
                                } else {
                                    Color.White
                                }, RoundedCornerShape(8.dp)
                            )
                            .padding(20.dp)
                    ) {
                        Canvas(modifier = Modifier.fillMaxSize()) {
                            val dashPathEffect =
                                androidx.compose.ui.graphics.PathEffect.dashPathEffect(
                                    floatArrayOf(30f, 30f), 0f
                                )
                            val roundedRectPath = Path().apply {
                                addRoundRect(
                                    RoundRect(
                                        rect = Rect(0f, 0f, size.width, size.height),
                                        radiusX = 16.dp.toPx(),
                                        radiusY = 16.dp.toPx()
                                    )
                                )
                            }
                            drawPath(
                                path = roundedRectPath,
                                color = Color.Blue,
                                style = Stroke(width = 6f, pathEffect = dashPathEffect)
                            )
                        }
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(10.dp)
                                .clip(RoundedCornerShape(8.dp))
                        ) {
                            Text(
                                text = stringResource(R.string.splash_welcome_to_quiz),
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally)
                                    .fillMaxWidth(),
                                fontSize = 26.sp,
                                fontWeight = FontWeight.ExtraBold,
                                lineHeight = 45.sp,
                                textAlign = TextAlign.Center
                            )
                            Text(text = stringResource(R.string.splash_please_enter_your_name), modifier = Modifier.padding(start = 5.dp, top = 10.dp))
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 10.dp)
                                    .padding(horizontal = 5.dp)
                            ) {
                                TextField(
                                    singleLine = true,
                                    value = name,
                                    onValueChange = { if (it.length <= 20) { name = it } },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(56.dp),
                                    placeholder = { Text(text = stringResource(R.string.splash_type_name_here)) },
                                    colors = TextFieldDefaults.colors(
                                        focusedContainerColor = colorResource(id = R.color.main_blue),
                                        unfocusedContainerColor = colorResource(id = R.color.main_blue),
                                        cursorColor = Color.White,
                                        focusedIndicatorColor = Color.Transparent,
                                        unfocusedIndicatorColor = Color.Transparent
                                    ),
                                    textStyle = TextStyle(
                                        color = Color.White,
                                        fontSize = 16.sp,
                                        lineHeight = 20.sp
                                    )
                                )
                            }
                            if (name.length > 2) {
                                Button(
                                    onClick = {
                                        viewModel.processedAction(
                                            SplashAction.AddUserToDB(name, listOf(
                                            Category("general_knowledge"),
                                            Category("music"),
                                            Category("food_and_drink"),
                                            Category("film_and_tv"),
                                        )))
                                        SharedPreferencesRepository.setFirstLaunch()
                                        navHostController.navigate(Routes.MenuScreen.route) {
                                            popUpTo(navHostController.graph.startDestinationId) { inclusive = true }
                                        }
                                    },
                                    modifier = Modifier
                                        .align(Alignment.CenterHorizontally)
                                        .padding(top = 10.dp)
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
                                        .height(35.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        Color.Transparent,
                                        Color.White
                                    ),
                                ) {
                                    Text(text = stringResource(R.string.splash_continue))
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}


