package com.xynos.analyticservice.di

import com.xynos.analyticservice.service.LOG
import com.xynos.analyticservice.workmanager.SyncWorker
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface ServiceComponent {
    fun inject(log: LOG)
    fun inject(syncWorker: SyncWorker)

}