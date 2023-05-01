package com.example.data.local.dao

import androidx.room.*
import com.example.domain.entity.neo.Neo
import kotlinx.coroutines.flow.Flow

@Dao
interface NeoDao {
    @Query("SELECT * FROM neo_table ORDER BY date ASC")
    fun getNeo(): Flow<List<Neo>>

    @Query("DELETE FROM neo_table")
    suspend fun deleteNeoTable()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlanetary(neo: List<Neo>)

    @Update
    suspend fun updatePlanetary(neo: Neo)
}