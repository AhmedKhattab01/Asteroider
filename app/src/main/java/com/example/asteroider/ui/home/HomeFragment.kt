package com.example.asteroider.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.asteroider.R
import com.example.asteroider.databinding.FragmentHomeBinding
import com.example.data.mappers.MapPlanetaryResponseToPlanetary
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        lifecycleScope.launch {
            viewModel.getPlanetaryFromNetwork()
        }

        lifecycleScope.launch {
            viewModel.getNeoFromNetwork()
        }

        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.planetaryResponse.collect {
                if (it != null) {
                    val x = MapPlanetaryResponseToPlanetary.mapPlanetaryResponseToPlanetary(it)
                    viewModel.insertPlanetary(x)
                    viewModel.getPlanetaryFromLocal()
                    Log.e("a7a", "onCreateView: ${viewModel.planetary.value?.imageUrl}")

                }
            }
        }

        lifecycleScope.launch(Dispatchers.Main) {

            viewModel.planetary.collect { planetary ->
                if (planetary != null) {
                    binding.textView.text = planetary.explanation

                    Log.e("a7a", "planet: ${planetary.imageUrl}", )
                    Glide.with(this@HomeFragment)
                        .load(planetary.imageUrl)
                        .diskCacheStrategy(DiskCacheStrategy.DATA)
                        .into(binding.ivImageOfDay)
                }

            }
        }

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}