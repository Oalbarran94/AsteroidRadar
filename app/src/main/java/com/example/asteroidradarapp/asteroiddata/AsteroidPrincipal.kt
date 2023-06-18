package com.example.asteroidradarapp.asteroiddata

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.asteroidradarapp.R
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

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.asteroid_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.view_today_asteroids -> Log.i("view today asteroids","view today asteroids")
            R.id.view_saved_asteroids -> Log.i("view saved asteroids","view saved asteroids")
            R.id.view_week_asteroids -> Log.i("view week asteroids","view week asteroids")
        }
        return true
    }
}