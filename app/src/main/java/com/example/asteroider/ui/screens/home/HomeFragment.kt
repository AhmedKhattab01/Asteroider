package com.example.asteroider.ui.screens.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.asteroider.connectivity.ConnectivityObserver
import com.example.asteroider.connectivity.NetworkConnectivityObserver
import com.example.asteroider.databinding.FragmentHomeBinding
import com.example.asteroider.ui.utils.adapters.AsteroidsAdapter
import com.example.data.Util
import com.example.data.mappers.MapPlanetaryResponseToPlanetary
import com.example.data.mappers.NeoMappers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val planetaryViewModel: PlanetaryViewModel by activityViewModels()
    private val neoViewModel: NeoViewModel by activityViewModels()
    private lateinit var connectivityObserver: ConnectivityObserver

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)


        val adapter = AsteroidsAdapter()
        with(binding) {
            recyclerView.adapter = adapter
            progressBar.visibility = View.VISIBLE
            ivImageOfDay.visibility = View.GONE

            ivImageOfDay.setOnClickListener {
                if (planetaryViewModel.planetary.value != null) {
                    val planetary = planetaryViewModel.planetary.value
                    findNavController().navigate(
                        HomeFragmentDirections.actionHomeFragmentToPlanetaryDetailsFragment(
                            planetary!!
                        )
                    )
                }
            }

            tvSeeAll.setOnClickListener {
                val neo = neoViewModel.neo.value
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToNeoFragment2(neo!!.toTypedArray()))
            }
        }


        lifecycleScope.launch(Dispatchers.IO) {
            launch {
                planetaryViewModel.getPlanetaryFromLocal().collect {
                    planetaryViewModel.setPlanetary(it)

                    if (planetaryViewModel.planetary.value != null) {
                        loadImage(it.imageUrl)
                    }
                }
            }

            launch {
                neoViewModel.getNeoFromLocal().collect { neo ->
                    Log.e("a7a", "list: $neo")
                    neoViewModel.setNeo(neo)

                    if (neoViewModel.neo.value!!.isNotEmpty()) {
                        withContext(Dispatchers.Main) {
                            adapter.submitList(neo.filter { it.date == Util.getCurrentDate })
                        }
                    }
                }
            }

            connectivityObserver = NetworkConnectivityObserver(requireContext().applicationContext)
            connectivityObserver.observe().collect {
                // When network is unavailable
                if (it == ConnectivityObserver.Status.Unavailable) {
                    // processPlanetaryOnNetworkUnAvailable()
                }
                // When network is available
                else if (it == ConnectivityObserver.Status.Available) {

                    launch {
                        processNeoOnNetworkAvailable()
                    }

                    launch {
                        delay(4000)
                        processPlanetaryOnNetworkAvailable()
                    }
                }
            }
        }

        // Inflate the layout for this fragment
        return binding.root
    }


    private suspend fun processPlanetaryOnNetworkAvailable() {
        if (planetaryViewModel.planetary.value == null) {
            planetaryViewModel.getPlanetaryFromNetwork()
            planetaryViewModel.planetaryResponse.collect { response ->
                // if response not equal null
                // * map the response to planetary object
                // * insert the result to database
                if (response != null) {
                    val mapped =
                        MapPlanetaryResponseToPlanetary.mapPlanetaryResponseToPlanetary(
                            response
                        )
                    planetaryViewModel.insertPlanetary(mapped)
                }
            }
        }
    }

    private suspend fun processNeoOnNetworkAvailable() {
        neoViewModel.neo.collect {
            if ((it != null && it.isEmpty())) {
                Log.e("a7a", "processNeoOnNetworkAvailable: called")
                neoViewModel.getNeoFromNetwork()
                neoViewModel.neoResponse.collect { response ->
                    // if response not equal null
                    // * map the response to planetary object
                    // * insert the result to database
                    if (response != null) {
                        val mapped =
                            NeoMappers.mapNeoResponseToNeo(
                                response
                            )
                        neoViewModel.insertNeo(mapped)
                    }
                }
            }
        }
    }

    private suspend fun loadImage(string: String) {
        withContext(Dispatchers.Main) {
            Glide.with(this@HomeFragment).load(string)
                .into(binding.ivImageOfDay)

            binding.progressBar.visibility = View.INVISIBLE
            binding.ivImageOfDay.visibility = View.VISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}