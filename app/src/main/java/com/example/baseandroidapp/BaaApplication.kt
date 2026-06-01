package com.example.baseandroidapp

import android.app.Application
import androidx.lifecycle.LifecycleObserver
import dagger.hilt.android.HiltAndroidApp

/**
 * [Application] class for Baa
 */
@HiltAndroidApp
open class BaaApplication :
    Application(),
    LifecycleObserver