package com.example.mytestproject.workManager

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class WorkScheduler @Inject constructor() {


     fun scheduleSyncWorker(context: Context) {
      val workManager = WorkManager.getInstance(context)
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
        //   WorkManager.getInstance(this).cancelAllWork()

    }
}