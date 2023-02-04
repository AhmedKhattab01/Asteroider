package com.example.asteroider.ui.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.planetary.Planetary
import com.example.domain.entity.planetary.PlanetaryResponse
import com.example.domain.repo.PlanetaryRepository
import com.example.domain.usecase.GetPlanetaryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlanetaryViewModel @Inject constructor(
    private val planetaryRepository: PlanetaryRepository,
    private val getPlanetaryUseCase: GetPlanetaryUseCase
) : ViewModel() {

    private val _planetaryResponse: MutableStateFlow<PlanetaryResponse?> = MutableStateFlow(null)
    val planetaryResponse: StateFlow<PlanetaryResponse?> get() = _planetaryResponse

    private val _planetary: MutableStateFlow<Planetary?> = MutableStateFlow(null)
    val planetary: StateFlow<Planetary?> get() = _planetary

    // get planetary from local db
    fun getPlanetaryFromLocal() = planetaryRepository.getPlanetaryFromLocal()

    // insert planetary
    fun insertPlanetary(planetary: Planetary) = viewModelScope.launch(Dispatchers.IO) {
        planetaryRepository.insertPlanetary(planetary)
    }

    // get planetary from api
    fun getPlanetaryFromNetwork() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = try {
                getPlanetaryUseCase()
            } catch (e: Exception) {
                Log.e("Home ViewModel", e.message.toString())
                return@launch
            }

            if (response.isSuccessful && response.body() != null) {
                _planetaryResponse.value = response.body()
            }
        }
    }

    // set planetary value
    fun setPlanetary(planetary: Planetary?) {
        _planetary.value = planetary
    }
}