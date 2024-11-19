package com.example.kotlinquizgraduationproject.utils

import android.content.Context
import android.content.res.Configuration
import java.util.Locale

fun updateLocale(context: Context, languageCode: String) {
    val locale = Locale(languageCode)
    Locale.setDefault(locale)
    val config = Configuration(context.resources.configuration)
    config.setLocale(locale)
    context.resources.updateConfiguration(config, context.resources.displayMetrics)
}