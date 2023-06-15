package com.example.asteroidradarapp.asteroiddata

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.asteroidradarapp.databinding.FragmentAsteroidPrincipalBinding


class AsteroidPrincipal : Fragment() {

    private val viewModel: AsteroidPrincipalViewModel by lazy {
        ViewModelProvider(this, AsteroidPrincipalViewModel.Factory(requireActivity().application))[AsteroidPrincipalViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentAsteroidPrincipalBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        binding.asteroidRecycler.adapter = AsteroidAdapter(AsteroidAdapter.OnClickListener {
            viewModel.navigateToSelectedAsteroid(it)
        })

        viewModel.navigateToSelectedAsteroid.observe(viewLifecycleOwner) {
            if (null != it) {
                this.findNavController().navigate(AsteroidPrincipalDirections.actionAsteroidPrincipalToDetailFragment(it))
                viewModel.displayAsteroidDetailsComplete()
            }
        }

        return binding.root
    }
}