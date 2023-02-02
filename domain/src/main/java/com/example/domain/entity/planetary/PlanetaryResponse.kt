package com.example.domain.entity.planetary

import com.squareup.moshi.Json

data class PlanetaryResponse(
    @Json(name = "hdurl")
    val ImageUrl: String,
    val title: String,
    val date: String,
    val explanation: String
)