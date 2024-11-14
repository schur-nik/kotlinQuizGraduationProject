package com.example.kotlinquizgraduationproject.network.entity.Categories

import com.google.gson.annotations.SerializedName

data class MetadataResponse(
    @SerializedName("byCategory")
    val category: Map<String, Int>
)