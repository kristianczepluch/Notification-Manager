package com.example.notificationmanager.ViewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.notificationmanager.data.NotificationEntity
import com.example.notificationmanager.data.Repository

class NotificationListViewModel(application: Application): AndroidViewModel(application) {

    private val repository: Repository by lazy { Repository(application) }

    fun getNotificationsFromAppAndTimestamp(packageName: String, timestamp: Long): LiveData<List<NotificationEntity>>{
        return repository.getNotificationsFromApp(packageName, timestamp)
    }
}