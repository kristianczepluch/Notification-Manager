package com.example.notificationmanager.data

import android.app.Application
import androidx.lifecycle.LiveData

class Repository(application: Application) {

    private val detoxRuleDao: DetoxRulesDao
    private val notificationDao: NotificationDao
    private val allRules: LiveData<List<DetoxRuleEntity>>
    private val notificationDatabase: NotificationsDatabase = NotificationsDatabase.getDatabase(application)
    private val detoxRulesDatabase: DetoxRulesDatabase = DetoxRulesDatabase.getDatabase(application)

    init{
        notificationDao = notificationDatabase.getNotificationDao()
        detoxRuleDao = detoxRulesDatabase.getDetoxRuleDao()
        allRules = detoxRuleDao.getAllRules()
    }

    fun insertNotification(notification: NotificationEntity){
        NotificationsDatabase.databaseWriteExecutor.execute{
            notificationDao.insertNotification(notification)
        }
    }

    fun getNotificationsFromApp(packageName: String, timestamp: Long): LiveData<List<NotificationEntity>> {
        return notificationDao.getNotificationsFromPackageNameFromTimestamp(timestamp, packageName)
    }

    fun deleteDetoxRule(id: Int){
        DetoxRulesDatabase.databaseWriteExecutor.execute{
            detoxRuleDao.deleteDetoxRuleById(id)
        }
    }

    fun getNotificationFromTimeStampWithOrder(timestamp: Long, order: Int): LiveData<List<NotificationListItem>>{
        return if(order == 0) notificationDao.getAllNotificationsAtTimeOrderByNumber(timestamp)
        else notificationDao.getAllNotificationsAtTimeOrderByApp(timestamp)
    }

    fun updateDetoxRule(detoxRule: DetoxRuleEntity){
        DetoxRulesDatabase.databaseWriteExecutor.execute{
            detoxRuleDao.updateDetoxRule(detoxRule)
        }
    }

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

    fun getDetoxRule(id: Int) = detoxRuleDao.getDetoxRuleById(id)
}