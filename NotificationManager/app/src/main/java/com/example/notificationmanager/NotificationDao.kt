package com.example.notificationmanager

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NotificationDao {

    @Insert
    fun insertNotification(notification: NotificationEntity)

    @Query("SELECT COUNT(*) FROM NOTIFICATIONS_TABLE WHERE received_time > :timestamp GROUP BY packageName")
    fun getAllNotificationsFromTimestamp(timestamp: Long): LiveData<List<NotificationEntity>>

    @Query("SELECT * FROM NOTIFICATIONS_TABLE WHERE received_time > :timestamp AND packageName = :packageNameArg")
    fun getNotificationsFromPackageNameFromTimestamp(timestamp: Long, packageNameArg: String): LiveData<List<NotificationEntity>>

    @Query("DELETE FROM notifications_table")
    fun deleteAllNotifications()

}