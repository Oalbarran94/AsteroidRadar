package com.example.asteroidradarapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.asteroidradarapp.Asteroid
import com.example.asteroidradarapp.Constants
import com.example.asteroidradarapp.PictureOfDay
import com.example.asteroidradarapp.api.getLastDayToSearch
import com.example.asteroidradarapp.api.getTodayDateToSearch
import com.example.asteroidradarapp.api.parseAsteroidsJsonResult
import com.example.asteroidradarapp.network.AsteroidApi
import com.example.asteroidradarapp.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.lang.Exception

class AsteroidRepository(private val database: AsteroidDatabase) {

    val asteroids: LiveData<List<Asteroid>> = Transformations.map(database.asteroidDao.getAsteroids()) {
        it.asDomainModel()
    }

    val pictureOfDay: LiveData<PictureOfDay> =
        Transformations.map(database.pictureOfDayDao.getLastPicture()) {
            it?.asDomainModel()
        }

    suspend fun refreshAsteroids(){
        withContext(Dispatchers.IO){
            try{
                val asteroids = parseAsteroidsJsonResult(
                    JSONObject(
                        AsteroidApi.retrofitService.getAsteroidsData(
                            getTodayDateToSearch(),
                            getLastDayToSearch(),
                            Constants.API_KEY
                        ).string()
                    )
                )
                database.asteroidDao.insertAll(*asteroids.asDatabaseModel())
            } catch (e: Exception){
                Log.e("AsteroidRepository", "Error saving or getting asteroids: $e")
            }

        }
    }

    suspend fun refreshPictureOfDay() {
        withContext(Dispatchers.IO) {
            try {
                database.pictureOfDayDao.insert(
                    AsteroidApi.retrofitService.getPictureOfDay(Constants.API_KEY).asDatabaseModel()
                )
            } catch (e: Exception) {
                Log.e("AsteroidRepository", "Error getting or saving picture of the day: $e")
            }
        }
    }
}