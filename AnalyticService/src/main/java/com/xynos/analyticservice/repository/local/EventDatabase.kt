package com.xynos.analyticservice.repository.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.xynos.analyticservice.model.Event

@Database(entities = [Event::class], version = 1,  exportSchema = false)
abstract class EventDatabase: RoomDatabase() {
    abstract val eventDao: EventDao
}