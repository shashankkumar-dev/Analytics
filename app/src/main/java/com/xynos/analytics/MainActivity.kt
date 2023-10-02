package com.xynos.analytics

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.xynos.analyticservice.service.LOG

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        LOG.init(this)
    }

    fun onClick(view: View) {
        LOG.d("Hello World", "Hello World")
    }
}