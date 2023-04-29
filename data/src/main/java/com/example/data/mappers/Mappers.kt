package com.example.data.mappers

import com.example.domain.entity.neo.Neo
import com.example.domain.entity.neo.NeoResponse
import com.example.domain.entity.planetary.Planetary
import com.example.domain.entity.planetary.PlanetaryResponse

object Mappers {
    fun mapPlanetaryResponseToPlanetary(planetaryResponse: PlanetaryResponse) : Planetary {
        return Planetary(
            0,
            planetaryResponse.ImageUrl,
            planetaryResponse.title,
            planetaryResponse.date,
            planetaryResponse.explanation
        )
    }

    fun mapNeoResponseToNeo(response: NeoResponse): List<Neo> {
        return response.nearEarthObjects.flatMap { (date, neos) ->
            neos.map { neo ->
                val closeApproachData = neo.closeApproachData.first()
                Neo(
                    neo.id.toInt(),
                    neo.name,
                    date,
                    neo.absoluteMagnitude,
                    neo.estimatedDiameter.kilometers.estimatedDiameterMax,
                    neo.isHazardous,
                    closeApproachData.relativeVelocity.kilometersPerSecond,
                    closeApproachData.missDistance.astronomical
                )
            }
        }
    }
}