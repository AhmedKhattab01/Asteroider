package com.example.data.mappers

import com.example.domain.entity.planetary.Planetary
import com.example.domain.entity.planetary.PlanetaryResponse

object MapPlanetaryResponseToPlanetary {
    fun mapPlanetaryResponseToPlanetary(planetaryResponse: PlanetaryResponse) : Planetary {
        return Planetary(
            0,
            planetaryResponse.ImageUrl,
            planetaryResponse.title,
            planetaryResponse.date,
            planetaryResponse.explanation
        )
    }
}