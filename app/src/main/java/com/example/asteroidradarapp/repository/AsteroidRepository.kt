package com.example.asteroidradarapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.asteroidradarapp.Asteroid
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

    suspend fun refreshAsteroids(){
        withContext(Dispatchers.IO){

            try{
                val asteroids = parseAsteroidsJsonResult(
                    JSONObject(
                        AsteroidApi.retrofitService.getAsteroidsData(
                            "2023-06-11",
                            "2023-06-18",
                            "EfZZUhwn9m4lE958ThhBHzwyAK9IQYcQReKbUXtk"
                        )
                    )
                )
                database.asteroidDao.insertAll(*asteroids.asDatabaseModel())
            } catch (e: Exception){
                Log.e("AsteroidRepository", "Error saving asteroids: $e")
            }

        }
    }
}