package com.example.domain.entity.neo

import com.squareup.moshi.Json

data class NeoResponse(
    @Json(name = "near_earth_objects")
    val nearEarthObjects: Map<String, List<NeoObject>>
)