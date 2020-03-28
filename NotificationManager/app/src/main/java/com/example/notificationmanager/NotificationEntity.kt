package com.example.notificationmanager

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey as PrimaryKey1

@Entity(tableName = "notifications_table", indices = [Index(value = ["uniqueKey"], unique = true)])
class NotificationEntity(
    @PrimaryKey1(autoGenerate = true) val id: Int,
    val uniqueKey: String,
    val packageName: String,
    val appName: String,
    val title: String,
    val content: String,
    val ringermode: String,
    val timestampNot: Long,
    val received_time: Long
)