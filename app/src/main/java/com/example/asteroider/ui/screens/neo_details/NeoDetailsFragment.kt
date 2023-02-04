package com.example.asteroider.ui.screens.neo_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.asteroider.R
import com.example.asteroider.databinding.FragmentNeoDetailsBinding

class NeoDetailsFragment : Fragment() {
    private var _binding: FragmentNeoDetailsBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<NeoDetailsFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentNeoDetailsBinding.inflate(inflater, container, false)

        with(binding) {
            neo = args.neo
            if (args.neo.isHazardous) {
                ivHazardous.setImageResource(R.drawable.hazardous)
            }
            else {
                ivHazardous.setImageResource(R.drawable.normal)
            }
        }

        // Inflate the layout for this fragment
        return binding.root
    }


}