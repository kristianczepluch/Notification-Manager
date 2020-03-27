package com.example.notificationmanager.utils

import android.content.Intent
import android.content.pm.PackageManager
import java.lang.Exception

object Utils {

    @JvmStatic
    fun getAppIconFromPackageName(packageName: String) =
        NotificationManagerApplication.appContext.packageManager.getApplicationIcon(packageName)


    @JvmStatic
    fun getAppNameFromPackageName(packageName: String) : String {
        val pm = NotificationManagerApplication.appContext.packageManager
        val appInfo = pm.getApplicationInfo(packageName, PackageManager.GET_META_DATA)
        var resolvedAppName: String = appInfo.loadLabel(pm).toString()
        try{
            if(resolvedAppName.equals(packageName)){
                val lauchIntent: Intent?  = pm.getLaunchIntentForPackage(packageName)
                if(lauchIntent != null && lauchIntent.component != null){
                    val launchingComponentLabel = pm.getActivityInfo(lauchIntent.component ?: throw Exception(), PackageManager.GET_META_DATA).labelRes
                    if(launchingComponentLabel>0) {
                        val launcherActivityLabel = pm.getText(packageName,launchingComponentLabel,null)
                        if (launcherActivityLabel != null){
                            resolvedAppName = launcherActivityLabel.toString()
                        }
                    }
                }
            }
        }catch(e: Exception){
            return resolvedAppName
        }
        return resolvedAppName
    }

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
