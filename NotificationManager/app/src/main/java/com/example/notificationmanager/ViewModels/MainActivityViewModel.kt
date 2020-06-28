package com.example.notificationmanager.ViewModels

import android.app.Application
import android.util.SparseBooleanArray
import androidx.core.util.set
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.notificationmanager.data.DetoxRuleEntity
import com.example.notificationmanager.data.NotificationListItem
import com.example.notificationmanager.data.Repository
import com.example.notificationmanager.utils.Utils

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: Repository by lazy { Repository(application) }

    private var notificationsOverviewList: LiveData<List<NotificationListItem>> = getNotifications()

    private val currentPage = MutableLiveData(-1)

    private val currentTimeFilter = MutableLiveData(0)


    private val selectedItemIdList: ArrayList<Int> = ArrayList()
    val selectedItems: SparseBooleanArray = SparseBooleanArray()
    var actionMode: Boolean = false


    private var rulesOverviewList: LiveData<List<DetoxRuleEntity>> = getRules()


    init {
        for (i in 0..(notificationsOverviewList.value?.size ?: 0)) {
            selectedItems[i] = false
        }
    }

    fun deleteSelectedItems() {
        selectedItemIdList.forEach {
            repository.deleteDetoxRule(it)
        }
    }

    fun setItemSelect(position: Int, selected: Boolean, id: Int) {
        selectedItems[position] = selected
        if (selected) selectedItemIdList.add(id)
        else selectedItemIdList.remove(id)
    }

    fun getNotificationOverview(): LiveData<List<NotificationListItem>> {
        return notificationsOverviewList
    }

    fun getNotificationsWithTimefilter(filter: Int): LiveData<List<NotificationListItem>> {
        return repository.getNotificationsFromTimeStamp(Utils.timeFilterToTimestamp(filter))
    }

    fun getCurrentPage(): LiveData<Int> {
        return currentPage
    }

    fun setCurrentPage(step: Int) {
        currentPage.postValue(step)
    }

    fun getCurrentTimeFilter(): LiveData<Int> {
        return currentTimeFilter
    }

    fun setCurrentTimeFilter(filter: Int) {
        currentTimeFilter.postValue(filter)
    }

    fun getRulesOverview(): LiveData<List<DetoxRuleEntity>> {
        return rulesOverviewList
    }


    private fun getNotifications() = repository.getAllNotificationsFromToday()
    private fun getRules() = repository.getAllRules()

}
