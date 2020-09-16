package com.example.mytestproject

import android.app.Application
import android.util.Log
import androidx.work.Configuration
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.data.di.WeatherDataModule
import com.example.mytestproject.di.CityFilterModule
import com.example.mytestproject.di.DaggerWeatherDataComponent
import com.example.mytestproject.di.WeatherDataComponent
import com.example.mytestproject.workManager.DeleteOldWeatherDataWorker
import com.example.mytestproject.workManager.SampleWorkerFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class App : Application() {

    lateinit var weatherDataComponent: WeatherDataComponent

    @Inject
    lateinit var sampleWorkerFactory: SampleWorkerFactory

    override fun onCreate() {
        super.onCreate()
        weatherDataComponent = DaggerWeatherDataComponent.builder()
            .weatherDataModule(WeatherDataModule(applicationContext))
            .build()

        weatherDataComponent.inject(this)

        WorkManager.initialize(
            this,
            Configuration.Builder().setWorkerFactory(sampleWorkerFactory).build()
        )

        scheduleSyncWorker()
    }

    private fun scheduleSyncWorker() {
        val workManager by lazy { WorkManager.getInstance(this) }
        val workRequest =
            PeriodicWorkRequestBuilder<DeleteOldWeatherDataWorker>(24, TimeUnit.HOURS)
                .addTag("cancel")
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