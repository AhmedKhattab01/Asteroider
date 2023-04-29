package com.example.asteroider.ui.screens.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.asteroider.databinding.FragmentHomeBinding
import com.example.asteroider.ui.utils.adapters.AsteroidsAdapter
import com.example.asteroider.ui.utils.callback.ConnectionExceptionCallback
import com.example.data.Util
import com.example.domain.entity.neo.Neo
import com.example.domain.entity.planetary.Planetary
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment(), ConnectionExceptionCallback {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val asteroiderViewModel: AsteroiderViewModel by activityViewModels()

    private val adapter = AsteroidsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        asteroiderViewModel.connectionExceptionCallback = this
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        with(binding) {
            // Assign adapter to recycler view
            recyclerView.adapter = adapter

            // Hide planetary image view
            ivImageOfDay.visibility = View.GONE

            lifecycleScope.launch {
                asteroiderViewModel.getPlanetaryFromLocal().collect { planetary ->
                    // Handle planetary updates here
                    if (planetary != null) {
                        loadImage(planetary.imageUrl)

                        binding.ivImageOfDay.setOnClickListener {
                            navigateToPlanetaryFragment(planetary)
                        }
                    }
                }
            }

            lifecycleScope.launch {
                asteroiderViewModel.getNeoFromLocal().collect { neo ->
                    // Handle neo updates here
                    if (neo != null) {
                        adapter.submitList(neo.filter { it.date == Util.currentDate })

                        tvSeeAll.setOnClickListener {
                            navigateToNeoFullList(neo)
                        }
                    }
                }
            }
        }

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun navigateToNeoFullList(neo: List<Neo>) {
        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToNeoFragment2(
                neo.toTypedArray()
            )
        )
    }

    private fun navigateToPlanetaryFragment(planetary: Planetary) {
        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToPlanetaryDetailsFragment(planetary)
        )
    }

    private suspend fun loadImage(string: String) {
        withContext(Dispatchers.Main) {
            // Load image with glide from passed string
            Glide.with(this@HomeFragment).load(string)
                .into(binding.ivImageOfDay)

            // show planetary after loading
            binding.ivImageOfDay.visibility = View.VISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override suspend fun onExceptionCaught() {
        withContext(Dispatchers.Main) {
            Toast.makeText(requireContext(), "No internet connection", Toast.LENGTH_SHORT).show()
        }
    }
}