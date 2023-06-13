package com.example.asteroidradarapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.asteroidradarapp.Asteroid
import com.example.asteroidradarapp.PictureOfDay
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
                            "2023-06-12",
                            "2023-06-14",
                            "EfZZUhwn9m4lE958ThhBHzwyAK9IQYcQReKbUXtk"
                        ).string()
                    )
                )

                Log.e("Result from asteroids", asteroids.toString())
                database.asteroidDao.insertAll(*asteroids.asDatabaseModel())
            } catch (e: Exception){
                Log.e("AsteroidRepository", "Error saving asteroids: $e")
            }

        }
    }

    suspend fun refreshPictureOfDay() {
        withContext(Dispatchers.IO) {
            try {
                database.pictureOfDayDao.insert(
                    AsteroidApi.retrofitService.getPictureOfDay("EfZZUhwn9m4lE958ThhBHzwyAK9IQYcQReKbUXtk").asDatabaseModel()
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}