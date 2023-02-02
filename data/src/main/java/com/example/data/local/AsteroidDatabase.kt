package com.example.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.local.dao.NeoDao
import com.example.data.local.dao.PlanetaryDao
import com.example.domain.entity.Neo
import com.example.domain.entity.Planetary

@Database(entities = [Neo::class, Planetary::class], version = 1, exportSchema = false)
abstract class AsteroidDatabase : RoomDatabase() {
    abstract fun getPlanetaryDao(): PlanetaryDao
    abstract fun getNeoDao(): NeoDao
}