package com.example.asteroider.ui.utils.callback

interface ConnectionExceptionCallback {
    suspend fun onExceptionCaught()
}