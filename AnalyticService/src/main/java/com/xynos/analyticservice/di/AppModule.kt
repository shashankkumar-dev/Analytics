package com.xynos.analyticservice.di

import com.google.firebase.firestore.FirebaseFirestore
import com.xynos.analyticservice.repository.remote.FirebaseRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

import android.content.Context
import androidx.room.Room
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.xynos.analyticservice.repository.local.EventDao
import com.xynos.analyticservice.repository.local.EventDatabase
import com.xynos.analyticservice.repository.local.LocalRepository
import com.xynos.analyticservice.service.Service
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val context: Context) {

    @Singleton
    @Provides
    fun provideApplicationContext(): Context = context

    @Provides
    @Singleton
    fun provideDatabase(context: Context): EventDatabase {
        return Room.databaseBuilder(context, EventDatabase::class.java, "events").build()
    }

    @Provides
    fun provideEventDao(db: EventDatabase): EventDao {
        return db.eventDao
    }

    @Provides
    fun provideLocalRepository(eventDao: EventDao) = LocalRepository(eventDao)

    @Provides
    fun provideCoroutineScope(): CoroutineScope = CoroutineScope(Dispatchers.IO)

    @Provides
    fun provideFirebase() = Firebase.firestore

    @Provides
    fun provideFirebaseRepository(db: FirebaseFirestore) = FirebaseRepository(db)

    @Provides
    fun provideService(
        context: Context,
        localRepository: LocalRepository,
        scope: CoroutineScope
    ): Service = Service(context, localRepository, scope)
}
