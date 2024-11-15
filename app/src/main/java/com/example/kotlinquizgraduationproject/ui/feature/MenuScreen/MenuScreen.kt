package com.example.kotlinquizgraduationproject.ui.feature.MenuScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.kotlinquizgraduationproject.R
import com.example.kotlinquizgraduationproject.repository.SharedPreferencesRepository
import com.example.kotlinquizgraduationproject.ui.feature.MenuScreen.domain.MenuAction
import com.example.kotlinquizgraduationproject.ui.navigation.Routes

@Preview(showBackground = true)
@Composable
fun MenuScreenPreview() {
    val navController = rememberNavController()
    MenuScreen(navHostController = navController)
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
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
//                        .align(Alignment.Center)
//                        .padding(bottom = 16.dp)
                        .fillMaxSize()
                ) {
                    Spacer(modifier = Modifier.padding(15.dp))
                    Text(text = "USERID = "+SharedPreferencesRepository.getUserId().toString())
                    Text(
                        text = stringResource(R.string.greating),
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(all = 100.dp)
                    )
                    SplashImage()
//                    Spacer(modifier = Modifier.height(200.dp))
                    Button(
                        onClick = {
                            navHostController.navigate(Routes.LevelsScreen.route)
                        },
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .width(200.dp)
                    ) {
                        Text("Levels")
                    }
                    Button(
                        onClick = {
//                            navHostController.navigate(Routes.Characters.route)
                        },
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .width(200.dp)
                    ) {
                        Text("Profile")
                    }
                    Button(
                        onClick = {
//                            navHostController.navigate(Routes.Characters.route)
                        },
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .width(200.dp)
                    ) {
                        Text("Exit")
                    }
                }
            }
        }
    )

}

@Composable
fun SplashImage() {
}