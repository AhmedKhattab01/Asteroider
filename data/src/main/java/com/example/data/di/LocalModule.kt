package com.example.data.di

import android.content.Context
import androidx.room.Room
import com.example.data.local.AsteroidDatabase
import com.example.data.local.dao.NeoDao
import com.example.data.local.dao.PlanetaryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {
    @Provides
    @Singleton
    fun providesPlanetaryDao(asteroidDatabase: AsteroidDatabase): PlanetaryDao {
        return asteroidDatabase.getPlanetaryDao()
    }

    @Provides
    @Singleton
    fun providesNeoDao(asteroidDatabase: AsteroidDatabase): NeoDao {
        return asteroidDatabase.getNeoDao()
    }

    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext context: Context): AsteroidDatabase {
        return Room.databaseBuilder(
            context,
            AsteroidDatabase::class.java,
            "asteroid_database"
        ).build()
    }
}