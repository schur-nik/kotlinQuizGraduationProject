package com.example.kotlinquizgraduationproject.ui.feature.LevelsScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinquizgraduationproject.ui.feature.LevelsScreen.domain.LevelsAction
import com.example.kotlinquizgraduationproject.ui.feature.LevelsScreen.domain.LevelsResult
import com.example.kotlinquizgraduationproject.ui.feature.LevelsScreen.domain.LevelsState
import com.example.kotlinquizgraduationproject.ui.feature.LevelsScreen.domain.usecases.ChangeFavoriteUseCase
import com.example.kotlinquizgraduationproject.ui.feature.LevelsScreen.domain.usecases.LoadFavoriteCategoriesUseCase
import com.example.kotlinquizgraduationproject.ui.feature.LevelsScreen.domain.usecases.LoadLevelProgressUseCase
import com.example.kotlinquizgraduationproject.ui.feature.LevelsScreen.domain.usecases.LoadQuestionCategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LevelsViewModel @Inject constructor(
    private val loadQuestionCategoriesUseCase: LoadQuestionCategoriesUseCase,
    private val loadLevelProgressUseCase: LoadLevelProgressUseCase,
    private val loadFavoriteCategoriesUseCase: LoadFavoriteCategoriesUseCase,
    private val changeFavoriteUseCase: ChangeFavoriteUseCase
) : ViewModel() {


    val state = MutableStateFlow(LevelsState())

    init {
        processedAction(LevelsAction.Init)
        processedAction(LevelsAction.LoadProgress)
        processedAction(LevelsAction.LoadFavorites)
    }

    fun processedAction(action: LevelsAction) {
        viewModelScope.launch {
            when (action) {
                is LevelsAction.Init -> loadQuestionCategoriesUseCase.loadListOfQuestionCategories()
                is LevelsAction.LoadProgress -> loadLevelProgressUseCase.loadLevelProgress()
                is LevelsAction.LoadFavorites -> loadFavoriteCategoriesUseCase.loadFavoriteCategories()
                is LevelsAction.ChangeFavorite -> changeFavoriteUseCase.changeFavorite(action.category, action.favorite)
            }.collect { result ->
                handleResult(result)
            }
        }

    }

    private suspend fun handleResult(result: LevelsResult) {
        when (result) {
            is LevelsResult.Loading -> {
                state.emit(state.value.copy(isLoading = true))
            }

            is LevelsResult.QuestionCategoriesListLoaded -> {
                state.emit(
                    state.value.copy(
                        isLoading = false,
                        listCategory = result.listCategory
                    )
                )
            }

            is LevelsResult.Failure -> {
                state.emit(
                    state.value.copy(
                        isLoading = false,
                    )
                )
            }

            is LevelsResult.LevelsProgressLoaded -> {
                state.emit(
                    state.value.copy(
                        listProgress = result.listProgress,
                    )
                )
            }

            is LevelsResult.FavoriteCategoriesLoaded -> {
                state.emit(
                    state.value.copy(
                        listFavorites = result.listFavorites,
                    )
                )
            }

            is LevelsResult.FavoriteCategoriesChanged -> {
                state.emit(
                    state.value.copy(
                        listFavorites = result.listFavorites,
                    )
                )
            }
        }
    }

}