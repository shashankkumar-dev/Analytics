package com.xynos.analyticservice.workmanager

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.xynos.analyticservice.di.AppModule
import com.xynos.analyticservice.di.DaggerServiceComponent
import com.xynos.analyticservice.repository.local.LocalRepository
import com.xynos.analyticservice.repository.remote.FirebaseRepository
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class SyncWorker @Inject constructor(
    val context: Context,
    params: WorkerParameters,
) : CoroutineWorker(context, params) {

    @Inject
    lateinit var localRepository: LocalRepository
    @Inject
    lateinit var firebaseRepository: FirebaseRepository


    override suspend fun doWork(): Result {
        if (!::localRepository.isInitialized) {
            init()
        }
        Log.i(TAG, "doWork: ")
        localRepository.getAllEvents().collect { list ->
            coroutineScope {
                list.forEach { event ->
                    launch {
                        if (firebaseRepository.uploadEventToFirebase(event)) {
                            localRepository.deleteEvent(event).collect()
                        }
                    }
                }
            }
        }
        return Result.success()
    }

    private fun init() {
        val serviceComponent = DaggerServiceComponent.builder().appModule(AppModule(context.applicationContext)).build()
        serviceComponent.inject(this)
    }

    companion object {
        private const val TAG = "SyncWorker"
    }
}