package com.example.mytestproject.di

import androidx.work.ListenableWorker
import com.example.mytestproject.workManager.ChildWorkerFactory
import com.example.mytestproject.workManager.DeleteOldWeatherDataWorker
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import kotlin.reflect.KClass



@MapKey
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class WorkerKey(val value: KClass<out ListenableWorker>)

@Module
interface WorkerBindingModule {

    @Binds
    @IntoMap
    @WorkerKey(DeleteOldWeatherDataWorker::class)
    fun bindDeleteOldWeatherDataWorker(factory: DeleteOldWeatherDataWorker.Factory): ChildWorkerFactory
}