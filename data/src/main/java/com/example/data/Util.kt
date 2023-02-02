package com.example.data

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object Util {
    val getCurrentDate: String = LocalDateTime.now()
        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
}