package com.example.notificationmanager.services

import android.app.Notification
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import androidx.lifecycle.LifecycleObserver
import com.example.notificationmanager.algs.NotificationRuleMachine
import com.example.notificationmanager.data.NotificationHandler
import java.util.concurrent.TimeUnit

class NotificationControlService() : NotificationListenerService(), LifecycleObserver {


    lateinit var notificationHandler: NotificationHandler
    lateinit var notificationRuleMachine: NotificationRuleMachine

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

            val rule_broken = notificationRuleMachine.isNotificationAllowed(sbn.packageName)

            if (rule_broken == (-1).toLong()) {
                Log.d("KristianDebug", "Notification from $packageName deleted because of supress-rule.")
                cancelNotification(sbn.key)
            } else if (rule_broken == -2L) {
                Log.d("KristianDebug", "Notification from $packageName deleted because of limit-rule")
                cancelNotification(sbn.key)
            } else if (rule_broken > 0) {
                val minutes = TimeUnit.MILLISECONDS.toMinutes(rule_broken)
                Log.d("KristianDebug", "Notification from $packageName snoozed for $minutes Minutes.")
                snoozeNotification(sbn.key, rule_broken)
            } else {
                notificationHandler.logNotification(sbn)
            }
        }
    }

    override fun onListenerConnected() {
        super.onListenerConnected()
        notificationHandler =
            NotificationHandler(application)
        notificationRuleMachine = NotificationRuleMachine(application)
    }
}