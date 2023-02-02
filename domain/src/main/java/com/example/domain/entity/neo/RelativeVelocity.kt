package com.example.domain.entity.neo

import com.squareup.moshi.Json

data class RelativeVelocity(
    @Json(name = "kilometers_per_second")
    val kilometersPerSecond: String
)