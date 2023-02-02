package com.example.domain.repo

import com.example.domain.entity.Neo
import com.example.domain.entity.neo.NeoResponse
import retrofit2.Response

interface NeoRepository {
    suspend fun getNeoFromNetwork() : Response<NeoResponse>

    fun getNeoFromLocal(): List<Neo>
    suspend fun insertNeo(neo: List<Neo>)

}