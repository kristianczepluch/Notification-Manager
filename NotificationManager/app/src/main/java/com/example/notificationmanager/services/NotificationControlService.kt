package com.example.notificationmanager.services

import android.app.Notification
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import com.example.notificationmanager.data.NotificationHandler

class NotificationControlService : NotificationListenerService() {

    lateinit var notificationHandler: NotificationHandler

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        super.onNotificationPosted(sbn)

        if (sbn != null) {

            // Ignoreing ongoing notifications
            if (sbn.isOngoing) return

            // Ignoring group summary notifications which are send along "normal" notifications
            if (sbn.notification.flags and Notification.FLAG_GROUP_SUMMARY != 0) return

            // Ignore foreground services
            if (sbn.notification.flags and Notification.FLAG_FOREGROUND_SERVICE != 0) return

            // Ongoing Events like Phonecalls etc..
            if (sbn.notification.flags and Notification.FLAG_ONGOING_EVENT != 0) return

            notificationHandler.logNotification(sbn)
        }
    }

    override fun onListenerConnected() {
        super.onListenerConnected()
        notificationHandler =
            NotificationHandler(application)
    }
}