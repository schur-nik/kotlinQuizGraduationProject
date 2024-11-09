package com.example.kotlinquizgraduationproject.network.entity.Catefories

import com.google.gson.annotations.SerializedName

data class MetadataResponse(
    @SerializedName("byCategory")
    val category: Map<String, Int>
)