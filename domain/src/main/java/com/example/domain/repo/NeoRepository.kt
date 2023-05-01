package com.example.domain.repo

import com.example.domain.entity.neo.Neo
import com.example.domain.entity.neo.NeoResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface NeoRepository {
    suspend fun getNeoFromNetwork() : Response<NeoResponse>
    fun getNeoFromLocal(): Flow<List<Neo>?>
    suspend fun insertNeo(neo: List<Neo>)


    suspend fun deleteNeoTable()

}