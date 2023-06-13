package com.example.asteroidradarapp.repository

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import com.example.asteroidradarapp.PictureOfDay

@Dao
interface PictureOfDayDao {
    @Query("SELECT * FROM picture_of_day_table")
    fun getLastPicture(): LiveData<PictureOfDayEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(pictureOfDay: PictureOfDayEntity)
}



@Entity(tableName = "picture_of_day_table")
data class PictureOfDayEntity(
    @PrimaryKey
    val url: String,
    val mediaType: String,
    val title: String,
)

fun PictureOfDayEntity.asDomainModel(): PictureOfDay {
    return PictureOfDay(
        mediaType = this.mediaType,
        title = this.title,
        url = this.url
    )
}