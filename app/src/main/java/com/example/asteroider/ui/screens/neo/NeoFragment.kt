package com.example.asteroider.ui.screens.neo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.asteroider.databinding.FragmentNeoBinding
import com.example.asteroider.ui.utils.adapters.AsteroidsAdapter

class NeoFragment : Fragment() {
    private var _binding: FragmentNeoBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<NeoFragmentArgs>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentNeoBinding.inflate(inflater, container, false)

        // initialize adapter
        val adapter = AsteroidsAdapter()
        binding.rvAsteroids.adapter = adapter

        // submit passed list
        adapter.submitList(args.neo.toMutableList())
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}