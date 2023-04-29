package com.example.asteroider.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.asteroider.ui.utils.callback.ConnectionExceptionCallback
import com.example.data.mappers.Mappers
import com.example.domain.entity.neo.Neo
import com.example.domain.entity.planetary.Planetary
import com.example.domain.repo.NeoRepository
import com.example.domain.repo.PlanetaryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AsteroiderViewModel @Inject constructor(
    private val neoRepository: NeoRepository,
    private val planetaryRepository: PlanetaryRepository,
) : ViewModel() {

    lateinit var connectionExceptionCallback: ConnectionExceptionCallback

    // get planetary from api
    fun getPlanetaryFromNetwork() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = try {
                planetaryRepository.getPlanetaryFromNetwork()
            } catch (e: Exception) {
                connectionExceptionCallback.onExceptionCaught()
                return@launch
            }

            if (response.isSuccessful && response.body() != null) {
                insertPlanetary(
                    Mappers.mapPlanetaryResponseToPlanetary(response.body()!!)
                )
            }
        }
    }

    // get neo from network
    fun getNeoFromNetwork() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = try {
                neoRepository.getNeoFromNetwork()
            } catch (e: Exception) {
                connectionExceptionCallback.onExceptionCaught()
                return@launch
            }

            if (response.isSuccessful && response.body() != null) {
                insertNeo(
                    Mappers.mapNeoResponseToNeo(response.body()!!)
                )
            }
        }
    }

    // get planetary from local db
    fun getPlanetaryFromLocal() = planetaryRepository.getPlanetaryFromLocal()

    fun getNeoFromLocal() = neoRepository.getNeoFromLocal()

    // insert planetary
    private fun insertPlanetary(planetary: Planetary) = viewModelScope.launch(Dispatchers.IO) {
        planetaryRepository.insertPlanetary(planetary)
    }

    // insert neo to db
    private fun insertNeo(neo: List<Neo>) = viewModelScope.launch(Dispatchers.IO) {
        neoRepository.insertNeo(neo)
    }
}




