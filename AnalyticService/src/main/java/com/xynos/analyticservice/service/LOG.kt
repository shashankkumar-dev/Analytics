package com.xynos.analyticservice.service

import android.content.Context
import android.util.Log
import com.xynos.analyticservice.di.AppModule
import com.xynos.analyticservice.di.DaggerServiceComponent
import javax.inject.Inject

class LOG private constructor(context: Context) {

    init {
        DaggerServiceComponent.builder()
            .appModule(AppModule(context))
            .build()
            .inject(this)
    }

    @Inject
    lateinit var service: Service

    private fun store(tag: String, message: String) {
        service.storeEventToDB(tag, message)
    }

    companion object {

        private var INSTANCE: LOG? = null

        fun init(context: Context) {
            if (INSTANCE == null) {
                synchronized(LOG::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = LOG(context.applicationContext)
                    }
                }
            }
        }

        fun i(tag: String, message: String) {
            Log.i(tag, message)
            INSTANCE?.store(tag, message)
        }

        fun d(tag: String, message: String) {
            Log.d(tag, message)
            INSTANCE?.store(tag, message)
        }

        fun e(tag: String, message: String) {
            Log.e(tag, message)
            INSTANCE?.store(tag, message)
        }
    }
}
