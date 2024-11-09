package com.example.kotlinquizgraduationproject.ui.feature.LevelsScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Preview(showBackground = true)
@Composable
fun LevelsScreenPreview() {
    val navController = rememberNavController()
    LevelsScreen(navHostController = navController)
}

@Composable
fun LevelsScreen(
    navHostController: NavHostController,
//    viewModel: LevelsViewModel = hiltViewModel()
) {

//    val state by viewModel.state.collectAsState()
    val list = listOf("science", "music", "food_and_drink", "film_and_tv")

    Scaffold(
        content = { padding ->
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(horizontal = 20.dp, vertical = 150.dp)
                        .fillMaxSize()
                ) {
//                    state.run {
//                        if (state.isLoading) {
//                            CircularProgressIndicator(
//                                modifier = Modifier.align(Alignment.CenterHorizontally)
//                            )
//                        }
                    if (list.isNotEmpty()) {
                        BlockCategory(
                            list = list,
                            onClick = { category ->
//                                    navHostController.navigate(Routes.CharacterDetail.createRoute(category.name))
                            }
                        )
                    }
//                    }
                }
            }
        }
    )

}

@Composable
fun BlockCategory(
    list: List<String>,
    onClick: (string: String) -> Unit = {}
) {
    if (list.isNotEmpty()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Cyan),
            contentPadding = PaddingValues(
                horizontal = 10.dp,
                vertical = 15.dp
            )
        ) {
            items(list.size) { element ->
                Column(
                    modifier = Modifier
                        .border(1.dp, Color.Red)
                        .fillMaxSize()
                        .padding(vertical = 10.dp)
                ) {
                    Text(list[element], fontSize = 24.sp)
                    Row(
                        modifier = Modifier
                            .border(1.dp, Color.Red)
                            .fillMaxSize()
                    ) {
                        Button(
                            onClick = {}, //{navHostController.navigate(Routes.Characters.route)},
                            modifier = Modifier.weight(0.3f).padding(5.dp)
                        ) {
                            Text(text = "Easy")
                        }
                        Button(
                            onClick = {}, //{navHostController.navigate(Routes.Characters.route)},
                            modifier = Modifier.weight(0.3f).padding(5.dp)
                        ) {
                            Text(text = "Medium")
                        }
                        Button(
                            onClick = {}, //{navHostController.navigate(Routes.Characters.route)},
                            modifier = Modifier.weight(0.3f).padding(5.dp)
                        ) {
                            Text(text = "Hard")
                        }
                    }
                }
            }
        }
    }
}
