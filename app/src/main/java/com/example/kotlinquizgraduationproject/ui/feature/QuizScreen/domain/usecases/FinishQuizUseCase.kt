package com.example.kotlinquizgraduationproject.ui.feature.QuizScreen.domain.usecases

import com.example.kotlinquizgraduationproject.model.quizinfo.LevelInformation
import com.example.kotlinquizgraduationproject.model.usersinfo.LevelProgress
import com.example.kotlinquizgraduationproject.repository.DBRepository
import com.example.kotlinquizgraduationproject.repository.SharedPreferencesRepository
import com.example.kotlinquizgraduationproject.ui.feature.QuizScreen.domain.QuizResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FinishQuizUseCase @Inject constructor(
    private val dbRepository: DBRepository
) {

    fun finishQuiz(levelInformation: LevelInformation, correctAnswersCount: Int): Flow<QuizResult> {
        return flow {
            val currLevelProgress = dbRepository.getLevelProgress(
                SharedPreferencesRepository.getUserId(),
                levelInformation.category,
                levelInformation.difficulty
            )
            if (currLevelProgress != null && currLevelProgress.progress < correctAnswersCount) {
                dbRepository.updateLevelProgress(
                    levelProgress = LevelProgress(
                        levelInformation.category,
                        levelInformation.difficulty,
                        correctAnswersCount
                    ),
                    userId = SharedPreferencesRepository.getUserId(),
                    progressId = currLevelProgress.id
                )
            }
            if (currLevelProgress == null) {
                dbRepository.addLevelProgress(
                    levelProgress = LevelProgress(
                        levelInformation.category,
                        levelInformation.difficulty,
                        correctAnswersCount
                    ),
                    userId = SharedPreferencesRepository.getUserId(),
                    0
                )
            }
            emit(QuizResult.FinishQuiz)
        }
    }

}