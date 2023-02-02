package com.example.asteroider.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.Util
import com.example.domain.entity.neo.NeoResponse
import com.example.domain.entity.planetary.PlanetaryResponse
import com.example.domain.usecase.GetNeoUseCase
import com.example.domain.usecase.GetPlanetaryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getNeoUseCase:GetNeoUseCase,
    val getPlanetaryUseCase: GetPlanetaryUseCase,

    ) : ViewModel() {
    private val _planetaryResponse: MutableStateFlow<PlanetaryResponse?> = MutableStateFlow(null)
    val planetaryResponse: StateFlow<PlanetaryResponse?> get() = _planetaryResponse

    private val _neoResponse: MutableStateFlow<NeoResponse?> = MutableStateFlow(null)
    val neoResponse: StateFlow<NeoResponse?> get() = _neoResponse

    fun getPlanetaryFromNetwork() {
        viewModelScope.launch {
            val response = try {
                getPlanetaryUseCase()
            } catch (e: Exception) {
                Log.e("Home ViewModel", e.message.toString())
                return@launch
            }

            if (response.isSuccessful && response.body() != null) {
                _planetaryResponse.value = response.body()

            }
            Log.e("Home ViewModel", _planetaryResponse.value.toString())
            Log.e("Home ViewModel", Util.getCurrentDate)
        }
    }

    fun getNeoFromNetwork() {
        viewModelScope.launch {
            val response = try {
                getNeoUseCase()
            } catch (e: Exception) {
                Log.e("Home ViewModel", e.message.toString())
                return@launch
            }

            if (response.isSuccessful && response.body() != null) {
                _neoResponse.value = response.body()

                _neoResponse.value?.nearEarthObjects?.forEach {
                    Log.e("a7a", "getNeoFromNetwork: ${it.value}", )
                }
            }

            Log.e("Home ViewModel", _neoResponse.value.toString())
        }
    }
}