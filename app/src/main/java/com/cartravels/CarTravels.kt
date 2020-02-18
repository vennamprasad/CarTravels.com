package com.cartravels

import android.app.Application
import com.cartravels.utils.LogUtils
import com.cartravels.utils.LogUtils.initLoggingUtilities
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
    }
}