package com.example.data.repo

import com.example.data.local.dao.PlanetaryDao
import com.example.data.network.ApiService
import com.example.domain.entity.planetary.Planetary
import com.example.domain.entity.planetary.PlanetaryResponse
import com.example.domain.repo.PlanetaryRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class PlanetaryRepoImpl(
    private val apiService: ApiService,
    private val planetaryDao: PlanetaryDao
) : PlanetaryRepository {
    override suspend fun getPlanetaryFromNetwork(): Response<PlanetaryResponse> =
        apiService.getPlanetaryData()

    override fun getPlanetaryFromLocal(): Flow<Planetary> = planetaryDao.getPlanetary()
    override suspend fun deletePlanetaryTable() {
        planetaryDao.deletePlanetaryTable()
    }

    override suspend fun insertPlanetary(planetary: Planetary) {
        planetaryDao.insertPlanetary(planetary)
    }
}