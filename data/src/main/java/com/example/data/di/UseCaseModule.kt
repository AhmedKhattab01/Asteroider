package com.example.data.di

import com.example.domain.repo.NeoRepository
import com.example.domain.repo.PlanetaryRepository
import com.example.domain.usecase.GetNeoUseCase
import com.example.domain.usecase.GetPlanetaryLocalUseCase
import com.example.domain.usecase.GetPlanetaryUseCase
import com.example.domain.usecase.InsertPlanetaryUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    // provide neo use case
    @Provides
    fun provideNeoUseCase(neoRepository: NeoRepository): GetNeoUseCase {
        return GetNeoUseCase(neoRepository)
    }

    // provide planetary use case
    @Provides
    fun providePlanetaryUseCase(planetaryRepository: PlanetaryRepository): GetPlanetaryUseCase {
        return GetPlanetaryUseCase(planetaryRepository)
    }

    // provide planetary from local usecase
    @Provides
    fun providePlanetaryFromLocalUseCase(planetaryRepository: PlanetaryRepository): GetPlanetaryLocalUseCase {
        return GetPlanetaryLocalUseCase(planetaryRepository)
    }

    // provide insert planetary use case
    @Provides
    fun provideInsertPlanetaryUseCase(planetaryRepository: PlanetaryRepository) : InsertPlanetaryUseCase {
        return InsertPlanetaryUseCase(planetaryRepository)
    }
}