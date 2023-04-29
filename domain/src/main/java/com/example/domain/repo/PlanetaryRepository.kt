package com.example.domain.repo

import com.example.domain.entity.planetary.Planetary
import com.example.domain.entity.planetary.PlanetaryResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface PlanetaryRepository {
    suspend fun getPlanetaryFromNetwork() : Response<PlanetaryResponse>

    fun getPlanetaryFromLocal(): Flow<Planetary?>

    suspend fun insertPlanetary(planetary: Planetary)
}