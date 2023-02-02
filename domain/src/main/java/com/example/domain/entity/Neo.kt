package com.example.domain.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "neo_table")
@Parcelize
data class Neo(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val date: String,
    val absoluteMagnitude : String,
    val estimatedDiameterMax : Double,
    val isHazardous : Boolean,
    val kiloMeterPerSecond : String,
    val astronomical: Double
) : Parcelable
