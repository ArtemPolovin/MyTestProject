package com.example.mytestproject.workManager

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WorkScheduler @Inject constructor(
    private val context: Context
) {

    private val workManager by lazy { WorkManager.getInstance(context) }

    fun scheduleDeleteOldWeatherDataWorker() {
        val workRequest =
            PeriodicWorkRequestBuilder<DeleteOldWeatherDataWorker>(24, TimeUnit.HOURS)
                .build()

        workManager.apply {
            enqueueUniquePeriodicWork(
                "delete old weather data",
                ExistingPeriodicWorkPolicy.KEEP,
                workRequest
            )
        }
    }

}