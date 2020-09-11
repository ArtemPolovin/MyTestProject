package com.example.mytestproject.di

import com.vikingsen.inject.worker.WorkerModule
import dagger.Module

@WorkerModule
@Module(includes = [WorkerInject_AssistModule::class])
abstract class AssistModule {

}