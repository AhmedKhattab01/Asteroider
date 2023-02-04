package com.example.data.local.dao

import androidx.room.*
import com.example.domain.entity.planetary.Planetary
import kotlinx.coroutines.flow.Flow

@Dao
interface PlanetaryDao {
    @Query("SELECT * FROM planetary_table LIMIT 1")
    fun getPlanetary(): Flow<Planetary>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlanetary(planetary : Planetary)

    @Update
    suspend fun updatePlanetary(planetary : Planetary)
}