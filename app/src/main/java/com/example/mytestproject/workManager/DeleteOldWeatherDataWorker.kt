package com.example.mytestproject.workManager

import android.content.Context
import android.util.Log
import androidx.work.ListenableWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.domain.useCase.weatherData.DeleteOldWeatherDataFromEntityUseCase
import com.example.mytestproject.util.CityDataCache
import javax.inject.Inject
import javax.inject.Provider


class DeleteOldWeatherDataWorker @Inject constructor(
    appContext: Context,
    workerParams: WorkerParameters,
    private val cityDataCache: CityDataCache,
    private val deleteOldWeatherDataFromEntityUseCase: DeleteOldWeatherDataFromEntityUseCase
) : Worker(appContext, workerParams) {

    override fun doWork(): Result {
        deleteOldWeatherDataFromEntityUseCase(cityDataCache.loadCityId())
        return Result.success()
    }

    class Factory @Inject constructor(
        private val cityDataCache: Provider<CityDataCache>,
        private val deleteOldWeatherDataFromEntityUseCase: Provider<DeleteOldWeatherDataFromEntityUseCase>
    ) : ChildWorkerFactory {
        override fun create(appContext: Context, params: WorkerParameters): ListenableWorker {
            return DeleteOldWeatherDataWorker(
                appContext,
                params,
                cityDataCache.get(),
                deleteOldWeatherDataFromEntityUseCase.get()
            )
        }

    }


}