package com.example.notificationmanager.data

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.notificationmanager.utils.Utils

class Repository(application: Application) {

    private val notificationDao: NotificationDao
    private val allNotifications: LiveData<List<NotificationListItem>>
    val database: NotificationsDatabase = NotificationsDatabase.getDatabase(application)

    init{
        notificationDao = database.getNotificationDao()
        allNotifications = notificationDao.getAllNotificationsFromTimestamp(Utils.getTodayDate().timeInMillis)
    }

    fun insertNotification(notification: NotificationEntity){
        NotificationsDatabase.databaseWriteExecutor.execute() {
            notificationDao.insertNotification(notification);
        }
    }
    fun getAllNotificationsFromToday() = allNotifications
}