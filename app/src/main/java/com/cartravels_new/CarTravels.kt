package com.cartravels_new

import android.app.Application
import com.cartravels_new.utils.LogUtils
import com.cartravels_new.utils.LogUtils.initLoggingUtilities
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import timber.log.Timber

/**
 * @author Steven Byle
 */
class CarTravels : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize logging first to log all operations
        initLoggingUtilities()
        Timber.v(LogUtils.METHOD_ONLY)
        FacebookSdk.sdkInitialize(applicationContext)
        AppEventsLogger.activateApp(this)
    }
}