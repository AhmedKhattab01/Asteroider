package com.example.domain.usecase

import com.example.domain.repo.PlanetaryRepository

class GetPlanetaryLocalUseCase(private val planetaryRepository: PlanetaryRepository) {
    //operator fun invoke() : Planetary = planetaryRepository.getPlanetaryFromLocal()
}