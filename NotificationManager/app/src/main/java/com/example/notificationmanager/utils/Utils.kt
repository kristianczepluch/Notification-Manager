package com.example.notificationmanager.utils

import android.content.Intent
import android.content.pm.PackageManager

object Utils {

    @JvmStatic
    fun getAppIconFromPackageName(packageName: String) =
        NotificationManagerApplication.appContext.packageManager.getApplicationIcon(packageName)


    @JvmStatic
    fun getAppNameFromPackageName(packageName: String) =
        NotificationManagerApplication.appContext.packageManager.getApplicationInfo(
            packageName,
            PackageManager.GET_META_DATA
        ).name

    @JvmStatic
    fun getAllInstalledApps(): List<String> {
        val pm = NotificationManagerApplication.appContext.packageManager
        val intent = Intent(Intent.ACTION_MAIN, null).addCategory(Intent.CATEGORY_LAUNCHER)
        val apkInfoList = pm.queryIntentActivities(intent, 0)
        val finalListList: ArrayList<String> = ArrayList()
        apkInfoList.forEach {
            finalListList.add(it.activityInfo.applicationInfo.packageName)
        }
        return finalListList
    }
}
