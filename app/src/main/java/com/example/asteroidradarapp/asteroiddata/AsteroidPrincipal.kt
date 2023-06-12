package com.example.asteroidradarapp.asteroiddata

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.asteroidradarapp.databinding.FragmentAsteroidPrincipalBinding


class AsteroidPrincipal : Fragment() {

    private val viewModel: AsteroidPrincipalViewModel by lazy {
        ViewModelProvider(this)[AsteroidPrincipalViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentAsteroidPrincipalBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        return binding.root
    }
}