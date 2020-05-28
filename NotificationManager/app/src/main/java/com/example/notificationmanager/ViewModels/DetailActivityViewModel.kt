package com.example.notificationmanager.ViewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.notificationmanager.data.DetoxRuleEntity
import com.example.notificationmanager.data.Repository

class DetailActivityViewModel (application: Application) : AndroidViewModel(application) {

    private val repository: Repository by lazy { Repository(application) }
    private var rulesItem: LiveData<DetoxRuleEntity> = MutableLiveData()


    fun getDetoxRuleEntity(id: Int): LiveData<DetoxRuleEntity>{
        rulesItem = repository.getDetoxRule(id)
        return rulesItem
    }
}