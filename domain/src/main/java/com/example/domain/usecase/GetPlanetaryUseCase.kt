package com.example.domain.usecase

import com.example.domain.repo.PlanetaryRepository

class GetPlanetaryUseCase(private val planetaryRepository: PlanetaryRepository) {
    suspend operator fun invoke() = planetaryRepository.getPlanetaryFromNetwork()
}