package com.example.asteroidradarapp.asteroiddata

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.asteroidradarapp.Asteroid
import com.example.asteroidradarapp.PictureOfDay
import com.example.asteroidradarapp.repository.AsteroidRepository
import com.example.asteroidradarapp.repository.getDatabase
import kotlinx.coroutines.launch

enum class AsteroidApiStatus { LOADING, ERROR, DONE }

class AsteroidPrincipalViewModel(application: Application) : AndroidViewModel(application){

    private val database = getDatabase(application)
    private val repository = AsteroidRepository(database)

    private val _status = MutableLiveData<AsteroidApiStatus>()
    val status: LiveData<AsteroidApiStatus>
        get() = _status

    init {
        _status.value = AsteroidApiStatus.LOADING
        viewModelScope.launch {
            try {
                repository.refreshAsteroids()
                _status.value = AsteroidApiStatus.DONE
                repository.refreshPictureOfDay()
            } catch (e: Exception) {
                _status.value = AsteroidApiStatus.ERROR
                Log.e("Error on view model", e.toString())
            }
        }
    }

    var asteroids: LiveData<List<Asteroid>> = repository.asteroids
    //var _asteroids: LiveData<List<Asteroid>> = repository.asteroids

    val pictureOfDay: LiveData<PictureOfDay> = repository.pictureOfDay

    private val _navigateToSelectedAsteroid = MutableLiveData<Asteroid?>()
    val navigateToSelectedAsteroid: LiveData<Asteroid?>
        get() = _navigateToSelectedAsteroid

    fun navigateToSelectedAsteroid(asteroid: Asteroid) {
        _navigateToSelectedAsteroid.value = asteroid
    }

    fun displayAsteroidDetailsComplete() {
        _navigateToSelectedAsteroid.value = null
    }

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