package com.example.kotlinquizgraduationproject.ui.feature.MenuScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.fontResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.res.ResourcesCompat.getFont
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.kotlinquizgraduationproject.R
import com.example.kotlinquizgraduationproject.database.dao.UserDao
import com.example.kotlinquizgraduationproject.database.entity.LevelProgressEntity
import com.example.kotlinquizgraduationproject.database.entity.UserEntity
import com.example.kotlinquizgraduationproject.repository.DBRepository
import com.example.kotlinquizgraduationproject.repository.SharedPreferencesRepository
import com.example.kotlinquizgraduationproject.ui.feature.MenuScreen.domain.MenuAction
import com.example.kotlinquizgraduationproject.ui.feature.MenuScreen.domain.usecases.AddUserToDBUseCase
import com.example.kotlinquizgraduationproject.ui.navigation.Routes

class FakeUserDao : UserDao {
    override suspend fun addUser(user: UserEntity): Long {
        TODO("Not yet implemented")
    }

    override suspend fun getUser(id: Int): UserEntity {
        TODO("Not yet implemented")
    }

    override suspend fun addLevelProgress(levelProgressEntity: LevelProgressEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun getProgress(u_id: Int): List<LevelProgressEntity> {
        TODO("Not yet implemented")
    }

}

@Preview(showBackground = true)
@Composable
fun MenuScreenPreview() {
    val navController = rememberNavController()
    MenuScreen(
        navHostController = navController,
        MenuViewModel(AddUserToDBUseCase(DBRepository(FakeUserDao())))
    )
}

@Composable
fun MenuScreen(
    navHostController: NavHostController,
    viewModel: MenuViewModel = hiltViewModel()
) {

    if (SharedPreferencesRepository.getUserId() == 0) {
        viewModel.processedAction(MenuAction.AddUserToDB)
    }

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
                        .fillMaxSize()
                        .padding(bottom = 50.dp),
                    contentScale = ContentScale.Crop
                )
                Column(
                    modifier = Modifier
//                        .align(Alignment.Center)
//                        .padding(bottom = 16.dp)
                        .fillMaxSize()
                ) {
                    Spacer(modifier = Modifier.padding(15.dp))
//                    Text(text = "USERID = "+SharedPreferencesRepository.getUserId().toString())
                    Text(
                        text = stringResource(R.string.greating),
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(horizontal = 60.dp)
                            .padding(top = 50.dp)
                            .height(250.dp),
                        fontSize = 45.sp,
                        fontWeight = FontWeight.ExtraBold,
                        lineHeight = 45.sp,
                        textAlign = TextAlign.Center
                    )
//                    SplashImage()
//                    Spacer(modifier = Modifier.height(200.dp))
                    MenuButton(stringResource(R.string.menubutton_levels), { navHostController.navigate(Routes.LevelsScreen.route) }, Modifier.align(Alignment.CenterHorizontally))
                    MenuButton(stringResource(R.string.menubutton_profile), {}, Modifier.align(Alignment.CenterHorizontally))
                    MenuButton(stringResource(R.string.menubutton_settings), {}, Modifier.align(Alignment.CenterHorizontally))
                    MenuButton(stringResource(R.string.menubutton_exit), {}, Modifier.align(Alignment.CenterHorizontally))
                }
            }
        }
    )

}

@Composable
private fun MenuButton(name: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        modifier = modifier
            .width(250.dp)
            .height(70.dp),
        contentPadding = PaddingValues(7.dp),
        colors = ButtonDefaults.buttonColors(
            Color.Transparent,
            Color.White
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            colorResource(R.color.main_blue),
                            Color.Black
                        ), // Задайте свои цвета
                        startY = 0f,
                        endY = 260f // Или используйте `endY = 1f` для нормализации
                    ),
                    shape = RoundedCornerShape(8.dp)
                )
        ) {
            Text(name, fontSize = 24.sp, modifier = Modifier.align(Alignment.Center))
        }
    }
}

@Composable
fun SplashImage() {
}