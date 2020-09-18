package com.example.mytestproject

import android.app.Application
import androidx.work.Configuration
import androidx.work.WorkManager
import com.example.data.di.WeatherDataModule
import com.example.mytestproject.di.DaggerWeatherDataComponent
import com.example.mytestproject.di.WeatherDataComponent
import com.example.mytestproject.workManager.SampleWorkerFactory
import com.example.mytestproject.workManager.WorkScheduler
import javax.inject.Inject

class App : Application() {

    lateinit var weatherDataComponent: WeatherDataComponent

    @Inject
    lateinit var sampleWorkerFactory: SampleWorkerFactory
    @Inject
    lateinit var workScheduler: WorkScheduler

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

        workScheduler.scheduleSyncWorker(this)
    }


}