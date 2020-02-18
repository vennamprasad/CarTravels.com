package com.cartravels.utils

import android.os.Bundle
import timber.log.Timber

/**
 * Simple logging wrapper to log messages for debugging.
 *
 * @author Steven Byle
 */
object LogUtils {
    const val METHOD_ONLY = DebugClassAndMethodTree.MESSAGE_SHOW_METHOD_ONLY
    private var sLoggingTree: Timber.Tree? = null
    @JvmStatic
    @Synchronized
    fun initLoggingUtilities() {
        if (sLoggingTree == null) {
            sLoggingTree = DebugClassAndMethodTree()
            Timber.plant(sLoggingTree as DebugClassAndMethodTree)
        }
    }

    @JvmStatic
    fun getSavedInstanceStateNullMessage(savedInstanceState: Bundle?): String {
        return "savedInstanceState" +
                (if (savedInstanceState == null) " == " else " != ") +
                "null"
    }
}