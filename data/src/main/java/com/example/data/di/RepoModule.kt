package com.example.data.di

import com.example.data.local.dao.NeoDao
import com.example.data.local.dao.PlanetaryDao
import com.example.data.network.ApiService
import com.example.data.repo.NeoRepoImpl
import com.example.data.repo.PlanetaryRepoImpl
import com.example.domain.repo.NeoRepository
import com.example.domain.repo.PlanetaryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {

    // provide neo repo
    @Provides
    fun provideNeoRepo(
        apiService: ApiService,
        neoDao: NeoDao
    ): NeoRepository {
        return NeoRepoImpl(apiService, neoDao)
    }

    // provide planetary repo
    @Provides
    fun providePlanetaryRepo(
        apiService: ApiService,
        planetaryDao: PlanetaryDao
    ): PlanetaryRepository {
        return PlanetaryRepoImpl(apiService, planetaryDao)
    }
}