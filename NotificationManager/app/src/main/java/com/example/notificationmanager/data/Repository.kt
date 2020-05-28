package com.example.notificationmanager.data

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.notificationmanager.utils.Utils

class Repository(application: Application) {

    private val detoxRuleDao: DetoxRulesDao
    private val notificationDao: NotificationDao
    private val allNotifications: LiveData<List<NotificationListItem>>
    private val allRules: LiveData<List<DetoxRuleEntity>>
    val notificationDatabase: NotificationsDatabase = NotificationsDatabase.getDatabase(application)
    val detoxRulesDatabase: DetoxRulesDatabase = DetoxRulesDatabase.getDatabase(application)

    init{
        notificationDao = notificationDatabase.getNotificationDao()
        detoxRuleDao = detoxRulesDatabase.getDetoxRuleDao()
        allNotifications = notificationDao.getAllNotificationsFromTimestamp(Utils.getTodayDate().timeInMillis)
        allRules = detoxRuleDao.getAllRules()
    }

    fun insertNotification(notification: NotificationEntity){
        NotificationsDatabase.databaseWriteExecutor.execute() {
            notificationDao.insertNotification(notification);
        }
    }

    fun deleteDetoxRule(id: Int){
        DetoxRulesDatabase.databaseWriteExecutor.execute{
            detoxRuleDao.deleteDetoxRuleById(id)
        }
    }

    fun getDetoxRule(id: Int) = detoxRuleDao.getDetoxRuleById(id)

    fun insertDetoxRule(detoxRule: DetoxRuleEntity){
        DetoxRulesDatabase.databaseWriteExecutor.execute() {
            detoxRuleDao.insertDetoxRule(detoxRule)
        }
    }

    fun deleteAllDetoxRules(){
        DetoxRulesDatabase.databaseWriteExecutor.execute() {
            detoxRuleDao.deleteAllDetoxRules()
        }
    }


    fun getAllRules() = allRules
    fun getAllNotificationsFromToday() = allNotifications
}