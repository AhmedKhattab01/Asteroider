package com.example.data.work_manager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.domain.repo.NeoRepository
import com.example.domain.repo.PlanetaryRepository

class MyWorker(
    context: Context,
    params: WorkerParameters,
    private val neoRepository: NeoRepository,
    private val planetaryRepository: PlanetaryRepository
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        return Result.success()
    }
}
