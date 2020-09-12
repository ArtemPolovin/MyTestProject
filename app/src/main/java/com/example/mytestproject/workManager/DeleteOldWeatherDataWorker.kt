package com.example.mytestproject.workManager

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.domain.useCase.weatherData.DeleteOldWeatherDataFromEntityUseCase
import com.example.mytestproject.util.CityDataCache
import com.squareup.inject.assisted.Assisted
import com.vikingsen.inject.worker.WorkerInject

class DeleteOldWeatherDataWorker @WorkerInject constructor(
    @Assisted private val appContext: Context,
    @Assisted private val workerParams: WorkerParameters,
    private val cityDataCache: CityDataCache,
    private val deleteOldWeatherDataFromEntityUseCase: DeleteOldWeatherDataFromEntityUseCase
) : Worker(appContext,workerParams) {

    override fun doWork(): Result {
        deleteOldWeatherDataFromEntityUseCase(cityDataCache.loadCityId())
        return Result.success()
    }


}