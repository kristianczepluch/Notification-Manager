package com.example.notificationmanager.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "notifications_table")
class NotificationEntity(
    val uniqueKey: String,
    val packageName: String,
    val appName: String,
    val title: String,
    val content: String,
    val timestampNot: Long,
    val received_time: Long
){
    public @PrimaryKey(autoGenerate = true) var id: Int = 0
}