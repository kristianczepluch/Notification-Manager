package com.example.notificationmanager.ViewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.notificationmanager.fragments.ruleCreation.SelectApplicationsFragment
import com.example.notificationmanager.utils.Utils

class RuleWizardViewModel(application: Application): AndroidViewModel(application) {

    val selectedRuleType: MutableLiveData<RuleType> by lazy { MutableLiveData<RuleType>(RuleType.SHORT_BREAK) }
    val selectedLimitNumberMode: MutableLiveData<LimitNumberMode> by lazy { MutableLiveData<LimitNumberMode>(LimitNumberMode.DAY) }
    val currentStep : MutableLiveData<Int> by lazy { MutableLiveData<Int>(0) }
    val selectedApplications: MutableLiveData<ArrayList<SelectApplicationsFragment.SelectAppListItem>> = MutableLiveData<ArrayList<SelectApplicationsFragment.SelectAppListItem>>(ArrayList())
    val selectedLimitNumber: MutableLiveData<Int> by lazy { MutableLiveData<Int>(1) }
    val selectedBreakTimeInMilliSeconds: MutableLiveData<Long> by lazy { MutableLiveData<Long>(Utils.minutesToMilliSeconds(30)) }


    init {
        val dataList = ArrayList<SelectApplicationsFragment.SelectAppListItem>()

        Utils.getAllInstalledApps().forEach{
            dataList.add(SelectApplicationsFragment.SelectAppListItem(it, false))
            selectedApplications.postValue(dataList)
        }
    }

    // All Getter Methodes for the RuleWizardUI
    fun getCurrentSteps(): LiveData<Int> = currentStep
    fun getSelectedRuleType(): LiveData<RuleType> = selectedRuleType
    fun getSelectedLimitNumberMode(): LiveData<LimitNumberMode> = selectedLimitNumberMode
    fun getSelectedApplications(): LiveData<ArrayList<SelectApplicationsFragment.SelectAppListItem>> = selectedApplications
    fun getSelectedLimitNumber(): LiveData<Int> = selectedLimitNumber
    fun getSelectedBreakTimeInMilliSeconds(): LiveData<Long> = selectedBreakTimeInMilliSeconds


    fun stepForward() { currentStep.postValue(currentStep.value?.plus(1) ) }
    fun stepBackwards() { currentStep.postValue(currentStep.value?.minus(1) ) }

    fun addAppToList(app: String){
        selectedApplications.value?.find { it.packageName.equals(app) }?.selected = true
        selectedApplications.postValue(selectedApplications.value)
    }

    fun removeAppFromList(app: String){
        selectedApplications.value?.find { it.packageName.equals(app) }?.selected = false
        selectedApplications.postValue(selectedApplications.value)
    }

    fun setSelectedRuleType(ruleType: RuleType) { selectedRuleType.value = ruleType }
    fun setSelectedLimitNumber(limitNumber: Int) { selectedLimitNumber.value = limitNumber }
    fun setSelectedBreakTimeInMilliSeconds(breakTimeInMilliseconds: Long) { selectedBreakTimeInMilliSeconds.value = breakTimeInMilliseconds}
    fun setSelectedLimitNumberMode(limitNumberMode: LimitNumberMode) { selectedLimitNumberMode.value = limitNumberMode}
}

enum class RuleType { ETERNALLY, LIMIT_NUMBER, SHORT_BREAK, SCHEDULE }

enum class LimitNumberMode { DAY, WEEK, HOUR }



