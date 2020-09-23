package com.flyn.kobe

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import java.lang.reflect.InvocationTargetException

class KobeApplication : Application() {


    object App {
        private var sApplication: Application? = null


        @JvmStatic
        fun init(app: Application) {
            if (sApplication == null) {
                sApplication = app
            }
        }

        @JvmStatic
        fun getContext(): Application? {
            if (sApplication != null) return sApplication
            try {
                @SuppressLint("PrivateApi")
                val activityThread = Class.forName("android.app.ActivityThread")
                val at = activityThread.getMethod("currentActivityThread").invoke(null)
                val app = activityThread.getMethod("getApplication").invoke(at)
                    ?: throw NullPointerException("u should init first")
                init(app as Application)
                return sApplication
            }
            catch (e: NoSuchMethodException) {
                Log.e("KOBE", "getApp 异常 ", e)
            }
            catch (e: IllegalAccessException) {
                Log.e("KOBE", "getApp 异常 ", e)
            }
            catch (e: InvocationTargetException) {
                Log.e("KOBE", "getApp 异常 ", e)
            }
            catch (e: ClassNotFoundException) {
                Log.e("KOBE", "getApp 异常 ", e)
            }

            throw NullPointerException("u should init first")
        }
    }

}