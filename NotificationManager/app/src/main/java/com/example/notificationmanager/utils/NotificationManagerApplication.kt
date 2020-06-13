package com.example.notificationmanager.utils

import android.app.Application
import android.content.Context
import android.content.res.Resources

/**     a wrapper around the application itself, to keep a static reference to
 *      the application context
 */

class NotificationManagerApplication : Application() {

    companion object {

        @JvmStatic
        lateinit var appContext: Context
            private set


        @JvmStatic
        val staticRessources: Resources
            get() = appContext.resources

        @JvmStatic
        fun getStaticStringRessource(res: Int): String {
            return appContext.resources.getString(res)
        }
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }
}

