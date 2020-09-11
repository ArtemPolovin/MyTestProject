package com.example.mytestproject

import android.app.Application
import androidx.work.Configuration
import com.example.data.di.WeatherDataModule
import com.example.mytestproject.di.CityFilterModule
import com.example.mytestproject.di.DaggerWeatherDataComponent
import com.example.mytestproject.di.WeatherDataComponent
import com.example.mytestproject.workManager.WorkScheduler
import com.vikingsen.inject.worker.WorkerFactory
import javax.inject.Inject

class App : Application(), Configuration.Provider {

    lateinit var weatherDataComponent: WeatherDataComponent
    @Inject
    lateinit var workerFactory: WorkerFactory
    @Inject
    lateinit var workScheduler: WorkScheduler

    override fun onCreate() {
        super.onCreate()
        weatherDataComponent = DaggerWeatherDataComponent.builder()
            .weatherDataModule(WeatherDataModule(applicationContext))
            .cityFilterModule(CityFilterModule(applicationContext))
            .build()

        weatherDataComponent.inject(this)

        workScheduler.scheduleDeleteOldWeatherDataWorker()

    }

    override fun getWorkManagerConfiguration(): Configuration =
        Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
}