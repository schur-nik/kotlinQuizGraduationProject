package com.example.kotlinquizgraduationproject.utils

import android.content.Context
import com.example.kotlinquizgraduationproject.R
import com.example.kotlinquizgraduationproject.model.quizinfo.Category

fun translateCategories(context: Context, listCategory: List<Category>): List<Category> {
    return listCategory.map { category ->
        val translatedName = when (category.name) {
            "sport_and_leisure" -> context.getString(R.string.sport_and_leisure)
            "science" -> context.getString(R.string.science)
            "food_and_drink" -> context.getString(R.string.food_and_drink)
            "music" -> context.getString(R.string.music)
            "general_knowledge" -> context.getString(R.string.general_knowledge)
            "history" -> context.getString(R.string.history)
            "arts_and_literature" -> context.getString(R.string.arts_and_literature)
            "film_and_tv" -> context.getString(R.string.film_and_tv)
            "society_and_culture" -> context.getString(R.string.society_and_culture)
            "geography" -> context.getString(R.string.geography)
            else -> category.name.replace("_", " ").capitalize()
        }
        Category(translatedName)
    }
}