package com.example.asteroider.ui.screens.planetary_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.asteroider.databinding.FragmentPlanetaryDetailsBinding


class PlanetaryDetailsFragment : Fragment() {
    private var _binding: FragmentPlanetaryDetailsBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<PlanetaryDetailsFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentPlanetaryDetailsBinding.inflate(inflater, container, false)

        // load image of day with glide from passed url string
        Glide.with(this).load(args.planetary.imageUrl).into(binding.ivOfDay)

        // set text views values from the received args
        with(binding) {
            tvTitle.text = args.planetary.title
            tvDate.text = args.planetary.date
            tvExplaination.text = args.planetary.explanation
        }

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}