package com.example.asteroidradarapp.repository

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import com.example.asteroidradarapp.Asteroid


@Dao
interface AsteroidDao {

    @Query("select * from asteroids_table WHERE closeApproachDate >= :date ORDER BY date(closeApproachDate) ASC")
    fun getAsteroids(date: String): LiveData<List<AsteroidEntity>>

    @Query("SELECT * FROM asteroids_table WHERE closeApproachDate = :date ORDER BY date(closeApproachDate) ASC")
    fun getTodaysAsteroids(date: String): LiveData<List<AsteroidEntity>>

    @Query("SELECT * FROM asteroids_table WHERE closeApproachDate >= :date ORDER BY date(closeApproachDate) ASC")
    fun getAsteroidsFromToday(date: String): LiveData<List<AsteroidEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg asteroids: AsteroidEntity)

    @Query("DELETE FROM asteroids_table WHERE closeApproachDate < :todaysDate")
    fun deleteOldData(todaysDate: String)

}

@Entity(tableName = "asteroids_table")
data class AsteroidEntity(
    @PrimaryKey
    val id: Long, val codename: String, val closeApproachDate: String,
    val absoluteMagnitude: Double, val estimatedDiameter: Double,
    val relativeVelocity: Double, val distanceFromEarth: Double,
    val isPotentiallyHazardous: Boolean
)

fun List<AsteroidEntity>.asDomainModel(): List<Asteroid> {
    return map {
        Asteroid(
            id = it.id,
            codename = it.codename,
            closeApproachDate = it.closeApproachDate,
            absoluteMagnitude = it.absoluteMagnitude,
            estimatedDiameter = it.estimatedDiameter,
            relativeVelocity = it.relativeVelocity,
            distanceFromEarth = it.distanceFromEarth,
            isPotentiallyHazardous = it.isPotentiallyHazardous
        )
    }
}