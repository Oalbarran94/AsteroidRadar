package com.example.asteroidradarapp.work

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.asteroidradarapp.repository.AsteroidRepository
import com.example.asteroidradarapp.repository.getDatabase
import retrofit2.HttpException

class RefreshDataWork(appContext: Context, params: WorkerParameters): CoroutineWorker(appContext, params) {

    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }

    override suspend fun doWork(): Result {
        Log.i("Am i running?", "Yes you are")
        val database = getDatabase(applicationContext)
        val repository = AsteroidRepository(database)

        repository.refreshAsteroids()

        return try {
            repository.refreshAsteroids()
            Result.success()
        } catch (exception: HttpException){
            Result.retry()
        }
    }
}