package com.example.data.mappers

import com.example.domain.entity.neo.Neo
import com.example.domain.entity.neo.NeoResponse

object NeoMappers {
    fun mapNeoResponseToNeo(response: NeoResponse): List<Neo> {
        val list: MutableList<Neo> = mutableListOf()

        response.nearEarthObjects.forEach { map ->
            map.value.forEach {
                list.add(
                    Neo(
                        it.id.toInt(),
                        it.name,
                        map.key,
                        it.absoluteMagnitude,
                        it.estimatedDiameter.kilometers.estimatedDiameterMax,
                        it.isHazardous,
                        it.closeApproachData[0].relativeVelocity.kilometersPerSecond,
                        it.closeApproachData[0].missDistance.astronomical
                    )
                )
            }
        }

        return list
    }
}