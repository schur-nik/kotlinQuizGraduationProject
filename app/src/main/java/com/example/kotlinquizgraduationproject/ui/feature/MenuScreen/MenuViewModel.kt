package com.example.kotlinquizgraduationproject.ui.feature.MenuScreen

import androidx.lifecycle.ViewModel
import com.example.kotlinquizgraduationproject.repository.ApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val apiRepository: ApiRepository
) : ViewModel() {

//    fun test() {
//        viewModelScope.launch {
//            val responseAllCharacters =
//                quizRepository.getListQuestions("easy", "science").body()?.map { questionItem ->
//                    Question(
//                        questionItem.correctAnswer,
//                        questionItem.incorrectAnswers,
//                        questionItem.question.text
//                    )
//                }
//            println(responseAllCharacters)
//
////            val responseAllCategories =
////                quizRepository.getListOfQuestionCategories().body()?.category?.map { categoryItem ->
////                    Category(
////                        categoryItem.key
////                    )
////                }
////            println(responseAllCategories)
//        }
//    }
}