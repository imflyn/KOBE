package com.flyn.kobe.utils

import android.util.Log

object L {

    private const val LOG_FORMAT = "%1\$s\n%2\$s"
    private const val TAG = "KOBE"

    @Volatile
    private var DISABLED = false

    private val functionName: String?
        get() {
            val sts = Thread.currentThread().stackTrace ?: return null

            sts.forEach {
                if (it.isNativeMethod) {
                    return@forEach
                }

                if (it.className == Thread::class.java.name) {
                    return@forEach
                }

                if (it.className == L::class.java.name) {
                    return@forEach
                }
                return "Thread:" + Thread.currentThread().name + "[" + it.fileName + "] line:" + it.lineNumber + "=="
            }
            return null
        }

    fun enableLogging() {
        DISABLED = false
    }

    fun disableLogging() {
        DISABLED = true
    }

    @JvmStatic
    fun d(message: String, vararg args: Any) {
        log(Log.DEBUG, null, message, *args)
    }

    @JvmStatic
    fun v(message: String, vararg args: Any) {
        log(Log.VERBOSE, null, message, *args)
    }

    @JvmStatic
    fun i(message: String, vararg args: Any) {
        log(Log.INFO, null, message, *args)
    }

    @JvmStatic
    fun w(message: String, vararg args: Any) {
        log(Log.WARN, null, message, *args)
    }

    @JvmStatic
    fun e(ex: Throwable) {
        log(Log.ERROR, ex, null)
    }

    @JvmStatic
    fun e(message: String, vararg args: Any) {
        log(Log.ERROR, null, message, *args)
    }

    @JvmStatic
    fun e(ex: Throwable, message: String, vararg args: Any) {
        log(Log.ERROR, ex, message, *args)
    }

    private fun log(priority: Int, ex: Throwable?, msg: String?, vararg args: Any) {
        var message = msg
        if (DISABLED) {
            return
        }
        if (args.isNotEmpty()) {
            message = String.format("" + message, *args)
        }

        val log: String?
        if (ex == null) {
            log = message
        } else {
            val logMessage = message ?: ex.message
            val logBody = Log.getStackTraceString(ex)
            log = String.format(LOG_FORMAT, logMessage, logBody)
        }

        Log.println(priority, TAG, createMessage(log))
    }

    private fun createMessage(msg: String?): String {
        return if (functionName == null) msg ?: "" else functionName + msg
    }

}