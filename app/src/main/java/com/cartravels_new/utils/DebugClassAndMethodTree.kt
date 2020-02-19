package com.cartravels_new.utils

import android.text.TextUtils
import org.jetbrains.annotations.NotNull
import timber.log.Timber.DebugTree

/**
 * @author Steven Byle
 */
class DebugClassAndMethodTree : DebugTree() {
    override fun log(priority: Int, tag: String?, msg: @NotNull String, t: Throwable?) {
        // Get the message prefix and prepend it into the message
        var message: String = msg
        val messagePrefix = messagePrefix
        if (!TextUtils.isEmpty(messagePrefix)) {
            val prefixedMessageBuilder = StringBuilder(messagePrefix)
            // Only append the message if it isn't empty, and it isn't the special directive only show method
            if (!TextUtils.isEmpty(message) && message != MESSAGE_SHOW_METHOD_ONLY) {
                prefixedMessageBuilder.append(": ").append(message)
            }
            message = prefixedMessageBuilder.toString()
        }
        super.log(priority, tag, message, t)
    }

    private val messagePrefix: String
        get() {
            val stackTrace = Throwable().stackTrace
            check(stackTrace.size > CALL_STACK_INDEX) { "Synthetic stacktrace didn't have enough elements: are you using proguard?" }
            return stackTrace[CALL_STACK_INDEX].methodName
        }

    companion object {
        const val MESSAGE_SHOW_METHOD_ONLY = "MESSAGE_SHOW_METHOD_ONLY"
        private const val CALL_STACK_INDEX = 6
    }
}