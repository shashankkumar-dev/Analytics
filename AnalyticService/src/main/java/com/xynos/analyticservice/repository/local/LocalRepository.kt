package com.xynos.analyticservice.repository.local

import android.util.Log
import com.xynos.analyticservice.model.Event
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class LocalRepository @Inject constructor(
    private val eventDao: EventDao
) {

    suspend fun insertEvent(event: Event): Flow<Boolean> = flow {
        try {
            Log.i(TAG, "insertEvent: $event")
            eventDao.insertEvent(event)
            emit(true)
        } catch (e: Exception) {
            Log.i(TAG, "insertEvent: $e")
            emit(false)
        }
    }

    suspend fun deleteEvent(event: Event): Flow<Boolean> = flow {
        try {
            eventDao.deleteEvent(event)
            emit(true)
        } catch (e: Exception) {
            Log.i(TAG, "deleteEvent: $e")
        }
    }

    suspend fun getAllEvents(): Flow<List<Event>> {
        return try {
            eventDao.getAllEvents()
        } catch (e: Exception) {
            Log.i(TAG, "getAllEvents: $e")
            flow {
                emit(listOf())
            }
        }
    }

    companion object {
        private const val TAG = "LocalRepository"
    }
}