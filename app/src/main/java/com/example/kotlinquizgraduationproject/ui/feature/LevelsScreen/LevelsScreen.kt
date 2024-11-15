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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.kotlinquizgraduationproject.R
import com.example.kotlinquizgraduationproject.model.quizinfo.Category
import com.example.kotlinquizgraduationproject.network.QuizApi
import com.example.kotlinquizgraduationproject.network.entity.Categories.MetadataResponse
import com.example.kotlinquizgraduationproject.network.entity.Questions.ListQuestionsResponse
import com.example.kotlinquizgraduationproject.repository.ApiRepository
import com.example.kotlinquizgraduationproject.ui.feature.LevelsScreen.domain.usecases.LoadQuestionCategoriesUseCase
import com.example.kotlinquizgraduationproject.ui.navigation.Routes
import com.example.kotlinquizgraduationproject.utils.translateCategories
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
fun LevelsScreenPreview() {
    val navController = rememberNavController()
    LevelsScreen(
        navHostController = navController, LevelsViewModel(LoadQuestionCategoriesUseCase(FakeQuizRepository().test()))
    )
}

@Composable
fun LevelsScreen(
    navHostController: NavHostController,
    viewModel: LevelsViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsState()
    var listCategory =  listOf(Category("Science"), Category("sport_and_leisure"), Category("general_knowledg"), Category("sport_and_leisure"))

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
                    state.run {
                        if (state.isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            )
                        }
                        if (listCategory.isNotEmpty()) {
                            BlockCategory(
                                list = translateCategories(LocalContext.current, listCategory),
                                onClick = { difficulty, category ->
                                    navHostController.navigate(
                                        Routes.QuizScreen.createRoute(
                                            difficulty,
                                            category
                                        )
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
    list: List<Category>,
    onClick: (string1: String, string2: String) -> Unit = { _: String, _: String -> }
) {
    if (list.isNotEmpty()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(),
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
                    Text(list[element].name, fontSize = 24.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 5.dp, bottom = 5.dp))
                    Row(
                        modifier = Modifier
                            .border(1.dp, Color.Red)
                            .fillMaxSize()
                    ) {
                        Button(
                            onClick = { onClick("easy", list[element].name) },
                            modifier = Modifier
                                .weight(0.3f)
                                .padding(1.dp),
                            colors = ButtonDefaults.buttonColors(colorResource(R.color.main_blue), Color.White),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(text = "Easy")
                        }
                        Button(
                            onClick = { onClick("medium", list[element].name) },
                            modifier = Modifier
                                .weight(0.3f)
                                .padding(1.dp),
                            colors = ButtonDefaults.buttonColors(colorResource(R.color.main_blue), Color.White),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(text = "Medium")
                        }
                        Button(
                            onClick = { onClick("hard", list[element].name) },
                            modifier = Modifier
                                .weight(0.3f)
                                .padding(1.dp),
                            colors = ButtonDefaults.buttonColors(colorResource(R.color.main_blue), Color.White),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(text = "Hard")
                        }
                    }
                }
            }
        }
    }
}
