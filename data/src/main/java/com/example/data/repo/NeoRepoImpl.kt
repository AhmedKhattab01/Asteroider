package com.example.data.repo

import com.example.data.local.dao.NeoDao
import com.example.data.network.ApiService
import com.example.domain.entity.neo.Neo
import com.example.domain.entity.neo.NeoResponse
import com.example.domain.repo.NeoRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class NeoRepoImpl(
    private val apiService: ApiService,
    private val neoDao: NeoDao
) : NeoRepository {
    override suspend fun getNeoFromNetwork(): Response<NeoResponse> = apiService.getNeoData()
    override fun getNeoFromLocal(): Flow<List<Neo>> = neoDao.getNeo()

    override suspend fun insertNeo(neo: List<Neo>) = neoDao.insertPlanetary(neo)
    override suspend fun deleteNeoTable() {
        neoDao.deleteNeoTable()
    }
}