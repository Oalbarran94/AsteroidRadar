package com.example.asteroidradarapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.asteroidradarapp.databinding.FragmentAsteroidPrincipalBinding


class AsteroidPrincipal : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentAsteroidPrincipalBinding.inflate(inflater, container, false)
        //val binding: FragmentAsteroidPrincipalBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_asteroid_principal, container, false)

        return binding.root
    }
}