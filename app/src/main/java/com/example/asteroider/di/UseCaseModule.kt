package com.example.asteroider.di

import com.example.domain.repo.NeoRepository
import com.example.domain.repo.PlanetaryRepository
import com.example.domain.usecase.GetNeoUseCase
import com.example.domain.usecase.GetPlanetaryUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    fun provideNeoUseCase(neoRepository: NeoRepository) : GetNeoUseCase {
        return GetNeoUseCase(neoRepository)
    }

    @Provides
    fun providePlanetaryUseCase(planetaryRepository: PlanetaryRepository) : GetPlanetaryUseCase {
        return GetPlanetaryUseCase(planetaryRepository)
    }
}