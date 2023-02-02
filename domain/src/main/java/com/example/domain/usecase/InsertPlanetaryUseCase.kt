package com.example.domain.usecase

import com.example.domain.entity.planetary.Planetary
import com.example.domain.repo.PlanetaryRepository

class InsertPlanetaryUseCase(private val planetaryRepository: PlanetaryRepository) {
    suspend operator fun invoke(planetary: Planetary) = planetaryRepository.insertPlanetary(planetary)
}