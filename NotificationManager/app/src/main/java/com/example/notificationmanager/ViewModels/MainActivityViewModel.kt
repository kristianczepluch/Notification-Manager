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

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: Repository by lazy { Repository(application) }

    private var notificationsOverviewList: LiveData<List<NotificationListItem>> = getNotifications()

    private val currentPage = MutableLiveData(-1)


    private val selectedItemIdList: ArrayList<Int> = ArrayList()
    val selectedItems: SparseBooleanArray = SparseBooleanArray()
    var actionMode: Boolean = false


    private var rulesOverviewList: LiveData<List<DetoxRuleEntity>> = getRules()


    init {
        for(i in 0..(notificationsOverviewList.value?.size ?: 0)){
            selectedItems[i] = false
        }
    }

    fun deleteSelectedItems(){
        selectedItemIdList.forEach{
            repository.deleteDetoxRule(it)
        }
    }

    fun setItemSelect(position: Int, selected: Boolean, id: Int){
        selectedItems[position] = selected
        if(selected) selectedItemIdList.add(id)
        else selectedItemIdList.remove(id)
    }

    fun getNotificationOverview() : LiveData<List<NotificationListItem>>{
        return notificationsOverviewList
    }

    fun getCurrentPage(): LiveData<Int>{
        return currentPage
    }

    fun setCurrentPage(step: Int){
        currentPage.postValue(step)
    }


    fun getRulesOverview() : LiveData<List<DetoxRuleEntity>>{
        return rulesOverviewList
    }

    private fun getNotifications() = repository.getAllNotificationsFromToday()
    private fun getRules() = repository.getAllRules()

}