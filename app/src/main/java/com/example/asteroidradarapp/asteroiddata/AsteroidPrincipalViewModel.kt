package com.example.asteroidradarapp.asteroiddata

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.asteroidradarapp.Asteroid
import com.example.asteroidradarapp.PictureOfDay
import com.example.asteroidradarapp.repository.AsteroidRepository
import com.example.asteroidradarapp.repository.getDatabase
import kotlinx.coroutines.launch

class AsteroidPrincipalViewModel(application: Application) : AndroidViewModel(application){

    private val database = getDatabase(application)
    private val repository = AsteroidRepository(database)

    init {
        viewModelScope.launch {
            try {
                repository.refreshAsteroids()
                repository.refreshPictureOfDay()
            } catch (e: Exception) {
                Log.e("Error on view model", e.toString())
            }
        }
    }

    private val asteroids: LiveData<List<Asteroid>> = repository.asteroids
    var _asteroids: LiveData<List<Asteroid>> = repository.asteroids

    val pictureOfDay: LiveData<PictureOfDay> = repository.pictureOfDay

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AsteroidPrincipalViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AsteroidPrincipalViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}