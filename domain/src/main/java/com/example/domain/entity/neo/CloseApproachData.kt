package com.example.domain.entity.neo

import com.squareup.moshi.Json

data class CloseApproachData(
    @Json(name = "relative_velocity")
    val relativeVelocity: RelativeVelocity,

    @Json(name = "miss_distance")
    val missDistance: MissDistance
)