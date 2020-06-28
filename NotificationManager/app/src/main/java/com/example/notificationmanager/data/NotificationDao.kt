package com.example.notificationmanager.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NotificationDao {

    @Insert
    fun insertNotification(notification: NotificationEntity)

    @Query("SELECT * FROM NOTIFICATIONS_TABLE WHERE received_time > :timestamp AND packageName = :packageNameArg ORDER BY received_time DESC")
    fun getNotificationsFromPackageNameFromTimestamp(timestamp: Long, packageNameArg: String): LiveData<List<NotificationEntity>>

    @Query("DELETE FROM notifications_table")
    fun deleteAllNotifications()

    @Query("SELECT packageName, appName, COUNT() FROM NOTIFICATIONS_TABLE WHERE received_time > :timestamp GROUP BY packageName ORDER BY COUNT() DESC")
    fun getAllNotificationsAtTimeOrderByNumber(timestamp: Long): LiveData<List<NotificationListItem>>

    @Query("SELECT packageName, appName, COUNT() FROM NOTIFICATIONS_TABLE WHERE received_time > :timestamp GROUP BY packageName ORDER BY appName ASC")
    fun getAllNotificationsAtTimeOrderByApp(timestamp: Long): LiveData<List<NotificationListItem>>

    @Query("SELECT * FROM NOTIFICATIONS_TABLE ")
    fun getAllNotifications(): LiveData<List<NotificationEntity>>

}
