package com.example.domain.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity("planetary_table")
data class Planetary(
    @PrimaryKey
    val id: Int,

    @ColumnInfo("image_url")
    val imageUrl: String,

    val title: String,

    val date: String,

    val explanation: String
) : Parcelable
