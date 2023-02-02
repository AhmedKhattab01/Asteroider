package com.example.data.local.dao

import androidx.room.*
import com.example.domain.entity.Neo

@Dao
interface NeoDao {
    @Query("SELECT * FROM neo_table")
    fun getNeo(): List<Neo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlanetary(neo: List<Neo>)

    @Update
    suspend fun updatePlanetary(neo: Neo)
}