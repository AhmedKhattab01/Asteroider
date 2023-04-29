package com.example.data.network


import com.example.data.Constants.API_KEY
import com.example.data.Constants.NEO_END_POINT
import com.example.data.Constants.PLANETARY_END_POINT
import com.example.data.Util
import com.example.domain.entity.neo.NeoResponse
import com.example.domain.entity.planetary.PlanetaryResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET(PLANETARY_END_POINT)
    suspend fun getPlanetaryData(
        @Query("api_key") apiKey : String = API_KEY
    ) : Response<PlanetaryResponse>

    @GET(NEO_END_POINT)
    suspend fun getNeoData(
        @Query("api_key") apiKey : String = API_KEY,
        @Query("start_date") startDate : String = Util.currentDate
    ) : Response<NeoResponse>
}