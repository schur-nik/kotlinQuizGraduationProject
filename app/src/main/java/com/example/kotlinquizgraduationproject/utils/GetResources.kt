package com.example.kotlinquizgraduationproject.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.example.kotlinquizgraduationproject.R
import com.example.kotlinquizgraduationproject.model.quizinfo.Category

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
