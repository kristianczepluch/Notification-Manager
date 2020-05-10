package com.example.notificationmanager.utils

import android.content.Intent
import android.content.pm.PackageManager
import com.example.notificationmanager.R
import com.example.notificationmanager.ViewModels.LimitNumberMode
import com.example.notificationmanager.ViewModels.RuleType
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

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
    fun getStaticStringRessource(ressourceID: Int) = NotificationManagerApplication.appContext.getString(ressourceID)

    @JvmStatic
    fun getStaticStringRessourceWithInt(ressourceID: Int, intToAdd: Int) = NotificationManagerApplication.appContext.getString(ressourceID, intToAdd)

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

    @JvmStatic
    fun getTodayDate() = setZeroTime(Calendar.getInstance())

    @JvmStatic
    fun setZeroTime(calender: Calendar) =
        calender.apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

    @JvmStatic
    fun getCurrentTime(): Long = System.currentTimeMillis()

    @JvmStatic
    fun minutesToMilliSeconds(min: Long): Long = TimeUnit.MINUTES.toMillis(min)

    @JvmStatic
    fun uiPositionToRuleType(position: Int) =
        when (position) {
            0 -> RuleType.SHORT_BREAK
            1 -> RuleType.SCHEDULE
            2 -> RuleType.LIMIT_NUMBER
            3 -> RuleType.ETERNALLY
            -1 -> RuleType.NOT_SELECTED
            else -> throw IllegalArgumentException("Unkown Ruletype")
        }

    @JvmStatic
    fun ruleTypeToUIPosition(ruleType: RuleType) =
        when (ruleType) {
            RuleType.SHORT_BREAK -> 0
            RuleType.SCHEDULE -> 1
            RuleType.LIMIT_NUMBER -> 2
            RuleType.ETERNALLY -> 3
            RuleType.NOT_SELECTED -> -1
        }

    @JvmStatic
    fun ruleTypeToUiString(ruleType: RuleType) =
        when (ruleType) {
            RuleType.SHORT_BREAK -> NotificationManagerApplication.appContext.resources.getStringArray(R.array.rule_names)[0].toString()
            RuleType.SCHEDULE -> NotificationManagerApplication.appContext.resources.getStringArray(R.array.rule_names)[1].toString()
            RuleType.LIMIT_NUMBER -> NotificationManagerApplication.appContext.resources.getStringArray(R.array.rule_names)[2].toString()
            RuleType.ETERNALLY -> NotificationManagerApplication.appContext.resources.getStringArray(R.array.rule_names)[3].toString()
            RuleType.NOT_SELECTED -> NotificationManagerApplication.appContext.resources.getStringArray(R.array.rule_names)[4].toString()
        }

    @JvmStatic
    fun limitNumberModeToUiString(limitNumberMode: LimitNumberMode) =
        when(limitNumberMode){
            LimitNumberMode.HOUR -> getStaticStringRessource(R.string.hourReview)
            LimitNumberMode.DAY-> getStaticStringRessource(R.string.dayReview)
            LimitNumberMode.WEEK -> getStaticStringRessource(R.string.weekReview)
            LimitNumberMode.NOT_SELECTED -> getStaticStringRessource(R.string.notSelected)
        }


}
