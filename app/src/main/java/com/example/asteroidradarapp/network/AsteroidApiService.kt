package com.example.asteroidradarapp.network

import android.util.Log
import com.example.asteroidradarapp.Asteroid
import com.example.asteroidradarapp.Constants
import com.example.asteroidradarapp.PictureOfDay
import com.example.asteroidradarapp.repository.AsteroidEntity
import com.example.asteroidradarapp.repository.PictureOfDayEntity
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(Constants.BASE_URL)
    .build()

interface AsteroidApiService {
    @GET("neo/rest/v1/feed")
    suspend fun getAsteroidsData(@Query("START_DATE") startDate: String, @Query("END_DATE") endDate: String, @Query("API_KEY") apiKey: String): ResponseBody

    @GET("planetary/apod")
    suspend fun getPictureOfDay(@Query("api_key") apiKey: String): PictureOfDay
}

object AsteroidApi {
    val retrofitService: AsteroidApiService by lazy {
        retrofit.create(AsteroidApiService::class.java)
    }
}


fun List<Asteroid>.asDatabaseModel(): Array<AsteroidEntity> {

    return map {
        AsteroidEntity(
            id = it.id,
            codename = it.codename,
            closeApproachDate = it.closeApproachDate,
            absoluteMagnitude = it.absoluteMagnitude,
            estimatedDiameter = it.estimatedDiameter,
            relativeVelocity = it.relativeVelocity,
            distanceFromEarth = it.distanceFromEarth,
            isPotentiallyHazardous = it.isPotentiallyHazardous
        )
    }.toTypedArray()
}

fun PictureOfDay.asDatabaseModel(): PictureOfDayEntity {
    return PictureOfDayEntity(
        mediaType = this.mediaType,
        title = this.title,
        url = this.url
    )
}