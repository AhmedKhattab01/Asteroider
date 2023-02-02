package com.example.domain.repo

import com.example.domain.entity.Planetary
import com.example.domain.entity.neo.NeoResponse
import com.example.domain.entity.planetary.PlanetaryResponse
import retrofit2.Response

interface PlanetaryRepository {
    suspend fun getPlanetaryFromNetwork() : Response<PlanetaryResponse>

    fun getPlanetaryFromLocal(): Planetary

    suspend fun insertPlanetary(planetary: Planetary)
}