package com.example.data.work_manager

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.data.mappers.MapPlanetaryResponseToPlanetary
import com.example.data.mappers.NeoMappers
import com.example.domain.repo.NeoRepository
import com.example.domain.repo.PlanetaryRepository

class MyWorker(
    context: Context,
    params: WorkerParameters,
    private val neoRepository: NeoRepository,
    private val planetaryRepository: PlanetaryRepository
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {

        suspend fun getPlanetary() {
            val response = planetaryRepository.getPlanetaryFromNetwork()

            Log.e("a7a", "getPlanetary: ", )
            if (response.isSuccessful && response.body() != null) {
                val planetary = response.body()
                val mapped = MapPlanetaryResponseToPlanetary.mapPlanetaryResponseToPlanetary(planetary!!)

                // add delete all

                planetaryRepository.insertPlanetary(mapped)
            }
        }

        suspend fun getNeoFromNetwork() {
            val response = neoRepository.getNeoFromNetwork()

            Log.e("a7a", "getPlanetary: ", )
            if (response.isSuccessful && response.body() != null) {
                val neo = response.body()
                val mapped = NeoMappers.mapNeoResponseToNeo(neo!!)

                // add delete all

                neoRepository.insertNeo(mapped)
            }
        }
        return Result.success()
    }
}
