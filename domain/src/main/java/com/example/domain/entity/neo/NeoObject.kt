package com.example.domain.entity.neo

import com.squareup.moshi.Json

data class NeoObject(
    @Json(name = "id")
    val id: String,

    val name : String,

    @Json(name = "absolute_magnitude_h")
    val absoluteMagnitude: String,

    @Json(name = "is_potentially_hazardous_asteroid")
    val isHazardous: Boolean,

    @Json(name = "estimated_diameter")
    val estimatedDiameter: EstimatedDiameter,

    @Json(name = "close_approach_data")
    val closeApproachData: List<CloseApproachData>
)