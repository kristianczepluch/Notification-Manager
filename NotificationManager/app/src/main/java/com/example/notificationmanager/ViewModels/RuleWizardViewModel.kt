package com.example.notificationmanager.ViewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.notificationmanager.fragments.ruleCreation.RuleTypeListItem
import com.example.notificationmanager.fragments.ruleCreation.SelectApplicationsFragment
import com.example.notificationmanager.utils.Utils

class RuleWizardViewModel(application: Application) : AndroidViewModel(application) {

    var selectedScheduleStartMinute: Int = 0
    var selectedScheduleEndMinute: Int = 0
    var selectedScheduleStartHour: Int = 0
    var selectedScheduleEndHour: Int = 0
    var selectedBreakTimeMinutes: Int = 0
    var selectedBreakTimeHours: Int = 0
    val selectedRuleTypeList = MutableLiveData(createDefaultRuleList())
    var selectedLimitNumberMode = LimitNumberMode.NOT_SELECTED
    val currentStep = MutableLiveData(0)
    val selectedApplications = createDefaultAppList()
    var selectedLimitNumber: Int = 1
    val enableNextButton: MutableLiveData<Boolean> = MutableLiveData(false)


    // All Getter Methodes for the RuleWizardUI
    fun getCurrentSteps(): LiveData<Int> = currentStep
    fun getEnableNextButton(): LiveData<Boolean> = enableNextButton

    fun stepForward() {
        currentStep.postValue(currentStep.value?.plus(1))
    }

    fun stepBackwards() {
        currentStep.postValue(currentStep.value?.minus(1))
    }

    fun addAppToList(app: String) {
        selectedApplications.find { it.packageName.equals(app) }?.selected = true
        selectedApplications.let { updatePrevButtonStep1(it) }
    }

    fun removeAppFromList(app: String) {
        selectedApplications.find { it.packageName.equals(app) }?.selected = false
        selectedApplications.let { updatePrevButtonStep1(it) }
    }

    fun getSelectedRuleTypeList(): LiveData<ArrayList<RuleTypeListItem>> = selectedRuleTypeList

    fun setSelectedRuleTypeInList(ruleType: RuleType) {
        selectedRuleTypeList.value?.forEach{
            it.selected = false
        }
        selectedRuleTypeList.value?.get(Utils.ruleTypeToUIPosition(ruleType))?.selected = true
        selectedRuleTypeList.postValue(selectedRuleTypeList.value)
    }


    private fun updatePrevButtonStep1(appList: ArrayList<SelectApplicationsFragment.SelectAppListItem>) {

        var atLeastOneAppSelected = false

        appList.forEach {
            if (it.selected) {
                atLeastOneAppSelected = true
            }
        }
        if (atLeastOneAppSelected) enableNextButton.postValue(true)
        else enableNextButton.postValue(false)

    }

    fun createDefaultAppList(): ArrayList<SelectApplicationsFragment.SelectAppListItem> {
        val dataList = ArrayList<SelectApplicationsFragment.SelectAppListItem>()
        Utils.getAllInstalledApps().forEach {
            dataList.add(SelectApplicationsFragment.SelectAppListItem(it, false))
        }
        return dataList
    }

    fun createDefaultRuleList(): ArrayList<RuleTypeListItem> {
        val result = ArrayList<RuleTypeListItem>()
        result.add(RuleTypeListItem(RuleType.SHORT_BREAK, false))
        result.add(RuleTypeListItem(RuleType.SCHEDULE, false))
        result.add(RuleTypeListItem(RuleType.LIMIT_NUMBER, false))
        result.add(RuleTypeListItem(RuleType.ETERNALLY, false))
        return result
    }

}

enum class RuleType { ETERNALLY, LIMIT_NUMBER, SHORT_BREAK, SCHEDULE }

enum class LimitNumberMode { DAY, WEEK, HOUR, NOT_SELECTED }



