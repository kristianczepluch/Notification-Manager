package com.example.notificationmanager.ViewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.notificationmanager.data.NotificationListItem
import com.example.notificationmanager.data.Repository

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: Repository by lazy { Repository(application) }

    private var notificationsOverviewList: LiveData<List<NotificationListItem>> = getNotifications()

    fun getNotificationOverview() : LiveData<List<NotificationListItem>>{
        return notificationsOverviewList
    }

    private fun getNotifications() = repository.getAllNotificationsFromToday()

}