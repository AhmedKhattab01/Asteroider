package com.example.asteroider.ui.screens.home

import android.os.Bundle
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

        // Initialize adapter
        val adapter = AsteroidsAdapter()

        with(binding) {
            // Assign adapter to recycler view
            recyclerView.adapter = adapter

            // Show progress bar
            progressBar.visibility = View.VISIBLE

            // Hide planetary image view
            ivImageOfDay.visibility = View.GONE

            // On image click listener
            // Navigate to planetary details fragment
            // pass args
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

            // navigate to neo Details fragment
            // pass args
            tvSeeAll.setOnClickListener {
                val neo = neoViewModel.neo.value
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToNeoFragment2(
                        neo!!.toTypedArray()
                    )
                )
            }
        }



        lifecycleScope.launch(Dispatchers.IO) {
            launch {
                // Collect planetary from local database
                planetaryViewModel.getPlanetaryFromLocal().collect {
                    // initialize planetary
                    planetaryViewModel.setPlanetary(it)

                    // load image if the image url field is not empty
                    if (planetaryViewModel.planetary.value != null) {
                        loadImage(it.imageUrl)
                    }
                }
            }

            launch {
                // Collect neo from local database
                neoViewModel.getNeoFromLocal().collect { neo ->
                    // initialize neo
                    neoViewModel.setNeo(neo)

                    // check if neo is not empty
                    if (neoViewModel.neo.value!!.isNotEmpty()) {
                        withContext(Dispatchers.Main) {
                            // if not empty submit list
                            adapter.submitList(neo.filter { it.date == Util.getCurrentDate })
                        }
                    }
                }
            }

            // Initialize connectivity observer
            connectivityObserver = NetworkConnectivityObserver(requireContext().applicationContext)
            // collect connectivity observer
            connectivityObserver.observe().collect {
                // When network is unavailable
                if (it == ConnectivityObserver.Status.Unavailable) {
                    // processPlanetaryOnNetworkUnAvailable()
                }
                // When network is available
                else if (it == ConnectivityObserver.Status.Available) {
                    // call neo on network available
                    launch {
                        processNeoOnNetworkAvailable()
                    }

                    // call planetary on network available
                    launch {
                        delay(3000)
                        processPlanetaryOnNetworkAvailable()
                    }
                }
            }
        }

        // Inflate the layout for this fragment
        return binding.root
    }


    private suspend fun processPlanetaryOnNetworkAvailable() {
        // check if planetary is null
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
            // check if neo is null or empty
            if ((it != null && it.isEmpty())) {
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
            // Load image with glide from passed string
            Glide.with(this@HomeFragment).load(string)
                .into(binding.ivImageOfDay)

            // Hide progress bar after loading
            binding.progressBar.visibility = View.INVISIBLE
            // show planetary after loading
            binding.ivImageOfDay.visibility = View.VISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}