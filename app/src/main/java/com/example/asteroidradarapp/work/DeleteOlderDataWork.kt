package com.example.asteroidradarapp.work

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.asteroidradarapp.api.getTodayDateToSearch
import com.example.asteroidradarapp.repository.getDatabase
import retrofit2.HttpException

class DeleteOlderDataWork(appContext: Context, params: WorkerParameters): CoroutineWorker(appContext, params) {
    companion object {
        const val WORK_NAME_DELETE = "DeleteOlderDataWorker"
    }

    override suspend fun doWork(): Result {
        val database = getDatabase(applicationContext)

        return try {
            Log.i("Am i running deleting?", "Yes you are")
            database.asteroidDao.deleteOldData(getTodayDateToSearch())
            Result.success()
        } catch (exception: HttpException){
            Result.retry()
        }
    }
}