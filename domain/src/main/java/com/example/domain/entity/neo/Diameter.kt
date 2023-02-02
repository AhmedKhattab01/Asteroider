package com.example.domain.entity.neo

import com.squareup.moshi.Json

data class Diameter(
    @Json(name = "estimated_diameter_max")
    val estimatedDiameterMax: Double
)