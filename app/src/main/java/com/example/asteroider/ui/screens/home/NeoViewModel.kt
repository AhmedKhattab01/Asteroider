package com.example.asteroider.ui.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.neo.Neo
import com.example.domain.entity.neo.NeoResponse
import com.example.domain.repo.NeoRepository
import com.example.domain.usecase.GetNeoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NeoViewModel @Inject constructor(
    private val getNeoUseCase: GetNeoUseCase,
    private val neoRepository: NeoRepository
) : ViewModel() {

    private val _neoResponse: MutableStateFlow<NeoResponse?> = MutableStateFlow(null)
    val neoResponse: StateFlow<NeoResponse?> get() = _neoResponse

    private val _neo: MutableStateFlow<List<Neo>?> = MutableStateFlow(null)
    val neo: StateFlow<List<Neo>?> get() = _neo

    fun getNeoFromLocal() = neoRepository.getNeoFromLocal()

    fun getNeoFromNetwork() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = try {
                getNeoUseCase()
            } catch (e: Exception) {
                Log.e("Home ViewModel", e.message.toString())
                return@launch
            }

            if (response.isSuccessful && response.body() != null) {
                _neoResponse.value = response.body()

                _neoResponse.value?.nearEarthObjects?.forEach {
                    Log.e("a7a", "getNeoFromNetwork: ${it.value}")
                }
            }

            Log.e("Home ViewModel", _neoResponse.value.toString())
        }
    }


    fun insertNeo(neo: List<Neo>) = viewModelScope.launch(Dispatchers.IO) {
        neoRepository.insertNeo(neo)
    }

    fun setNeo(neo: List<Neo>?) {
        _neo.value = neo
    }
}