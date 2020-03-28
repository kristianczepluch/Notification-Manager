package com.example.notificationmanager.services

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.widget.Toast

class NotificationControlService : NotificationListenerService() {


    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        super.onNotificationPosted(sbn)
        Toast.makeText(applicationContext, "Notification Received", Toast.LENGTH_SHORT).show()
    }


}