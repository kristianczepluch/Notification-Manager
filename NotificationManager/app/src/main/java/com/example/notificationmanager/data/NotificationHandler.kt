package com.example.notificationmanager.data

import android.app.Application
import android.service.notification.StatusBarNotification
import com.example.notificationmanager.utils.Utils

class NotificationHandler(application: Application) {

    private val repository: Repository =
        Repository(application)

    fun logNotification(sbn: StatusBarNotification) {
        repository.insertNotification(buildNotificationEntity(sbn))
    }

    private fun buildNotificationEntity(sbn: StatusBarNotification): NotificationEntity {
        val uniqueKey = sbn.key + sbn.notification.`when`.toString() +
                sbn.notification.extras.getString("android.title") +
                sbn.notification.extras.getString("android.text")

        return NotificationEntity(
            uniqueKey,
            sbn.packageName,
            Utils.getAppNameFromPackageName(sbn.packageName),
            sbn.notification.extras.getString("android.title") ?: "-",
            sbn.notification.extras.getString("android.text") ?: "-",
            sbn.notification.`when`,
            sbn.postTime
        )
    }
}