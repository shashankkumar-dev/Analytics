package com.xynos.analyticservice.service

import android.content.Context
import android.util.Log
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.xynos.analyticservice.model.Event
import com.xynos.analyticservice.repository.local.LocalRepository
import com.xynos.analyticservice.workmanager.SyncWorker
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class Service @Inject constructor(
    private val context: Context,
    private val localRepository: LocalRepository,
    private val scope: CoroutineScope
) {

    fun storeEventToDB(tag: String, message: String) {
        Log.i(TAG, "storeEventToDB:")
        val event = Event(tag, message)
        scope.launch {
            localRepository.insertEvent(event).collect {
                Log.i(TAG, "storeEventToDB: $it")
            }
        }
        sync()
    }

    private fun sync() {
        Log.i(TAG, "sync: ")
        val request = PeriodicWorkRequestBuilder<SyncWorker>(25, TimeUnit.MINUTES)
            .addTag(WORKER_TAG)
            .build()
        WorkManager.getInstance(context)
            .enqueueUniquePeriodicWork(WORKER_TAG, ExistingPeriodicWorkPolicy.KEEP,  request)
    }

    companion object {
        private const val TAG = "Service"
        private const val WORKER_TAG = "SyncWorker"
        private val handler = CoroutineExceptionHandler { _, throwable ->
            Log.e(TAG, "Error in coroutine: $throwable")
        }
    }
}