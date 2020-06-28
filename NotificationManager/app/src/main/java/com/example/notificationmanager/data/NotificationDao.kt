package com.example.notificationmanager.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NotificationDao {

    @Insert
    fun insertNotification(notification: NotificationEntity)

    @Query("SELECT packageName, COUNT() FROM NOTIFICATIONS_TABLE WHERE received_time > :timestamp GROUP BY packageName ORDER BY COUNT() DESC")
    fun getAllNotificationsFromTimestamp(timestamp: Long): LiveData<List<NotificationListItem>>

    @Query("SELECT * FROM NOTIFICATIONS_TABLE WHERE received_time > :timestamp AND packageName = :packageNameArg")
    fun getNotificationsFromPackageNameFromTimestamp(timestamp: Long, packageNameArg: String): LiveData<List<NotificationEntity>>

    @Query("DELETE FROM notifications_table")
    fun deleteAllNotifications()

    @Query("SELECT * FROM NOTIFICATIONS_TABLE ")
    fun getAllNotifications(): LiveData<List<NotificationEntity>>

}
