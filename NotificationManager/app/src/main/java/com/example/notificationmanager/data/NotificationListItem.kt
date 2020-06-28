package com.example.notificationmanager.data

import androidx.room.ColumnInfo

data class NotificationListItem(@ColumnInfo(name = "packageName") val packageName: String, @ColumnInfo(name = "appName") val appName: String,  @ColumnInfo(name = "COUNT()") val COUNT: Int)