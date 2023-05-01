package com.example.data.work_manager

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.data.mappers.Mappers
import com.example.domain.repo.NeoRepository
import com.example.domain.repo.PlanetaryRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class MyWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val planetaryRepository: PlanetaryRepository,
    private val neoRepository: NeoRepository
) :
    CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        return try {
            getNeoFromNetwork()
            getPlanetaryFromNetwork()
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }

    // get planetary from api
    private suspend fun getPlanetaryFromNetwork() {
        val response = try {
            planetaryRepository.getPlanetaryFromNetwork()
        } catch (e: Exception) {
            return
        }

        planetaryRepository.deletePlanetaryTable()

        if (response.isSuccessful && response.body() != null) {
            planetaryRepository.insertPlanetary(
                Mappers.mapPlanetaryResponseToPlanetary(response.body()!!)
            )
        }
    }

    // get neo from network
    private suspend fun getNeoFromNetwork() {
        val response = try {
            neoRepository.getNeoFromNetwork()
        } catch (e: Exception) {
            return
        }

        neoRepository.deleteNeoTable()

        if (response.isSuccessful && response.body() != null) {
            neoRepository.insertNeo(
                Mappers.mapNeoResponseToNeo(response.body()!!)
            )
        }
    }
}
