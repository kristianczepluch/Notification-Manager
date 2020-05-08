package com.example.notificationmanager.ViewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.notificationmanager.R
import com.example.notificationmanager.fragments.ruleCreation.RuleTypeListItem
import com.example.notificationmanager.fragments.ruleCreation.SelectApplicationsFragment
import com.example.notificationmanager.utils.NotificationManagerApplication
import com.example.notificationmanager.utils.Utils

class RuleWizardViewModel(application: Application) : AndroidViewModel(application) {

    var selectedWeekdayCheckboxes: BooleanArray = BooleanArray(7)

    var selectedScheduleStartMinute: Int = 0
    var selectedScheduleEndMinute: Int = 0
    var selectedScheduleStartHour: Int = 0
    var selectedScheduleEndHour: Int = 0

    private val selectedWeekdays: MutableLiveData<ArrayList<Weekdays>> = MutableLiveData(ArrayList<Weekdays>())

    private val selectedScheduleString = MutableLiveData("00:00 - 00:00")

    var selectedBreakTimeMinutes = 0
    var selectedBreakTimeHours = 0
    private val selectedBreakTimeString = MutableLiveData("0 Minutes")

    var selectedRuleType: RuleType = RuleType.SHORT_BREAK
    private val selectedRuleTypeList = MutableLiveData(createDefaultRuleList())

    private val selectedLimitNumberMode = MutableLiveData(LimitNumberMode.NOT_SELECTED)
    private val selectedLimitNumber = MutableLiveData(1)

    private val currentStep = MutableLiveData(0)

    private val selectedApplications: MutableLiveData<ArrayList<SelectApplicationsFragment.SelectAppListItem>> = MutableLiveData(createDefaultAppList())

    private val enableNextButton: MutableLiveData<Boolean> = MutableLiveData(false)


    fun getWeekdays(): LiveData<ArrayList<Weekdays>> = selectedWeekdays

    fun addWeekDay(weekday: Weekdays, position: Int) {
        selectedWeekdays.value?.add(weekday)
        selectedWeekdayCheckboxes[position] = true
        selectedWeekdays.postValue(selectedWeekdays.value)
    }

    fun removeWeekDay(weekday: Weekdays, position: Int) {
        selectedWeekdays.value?.remove(weekday)
        selectedWeekdayCheckboxes[position] = false
        selectedWeekdays.postValue(selectedWeekdays.value)
    }



    // All Getter Methodes for the RuleWizardUI

    fun setSelectedBreakTimeHour(hour: Int) {
        selectedBreakTimeHours = hour
        updateBreakTimeString()
    }

    fun setSelectedBreakTimeMinute(min: Int) {
        selectedBreakTimeMinutes = min
        updateBreakTimeString()
    }

    fun setScheduleStartHour(startHour: Int){
        selectedScheduleStartHour = startHour
        updateScheduleTimeString()
    }

    fun setScheduleStartMinute(startMinute: Int){
        selectedScheduleStartMinute = startMinute
        updateScheduleTimeString()
    }

    fun setScheduleEndHour(endHour: Int){
        selectedScheduleEndHour = endHour
        updateScheduleTimeString()
    }

    fun setScheduleEndMinute(endMinute: Int){
        selectedScheduleEndMinute = endMinute
        updateScheduleTimeString()
    }

    private fun updateScheduleTimeString(){

        val startString = if(selectedScheduleStartHour<10) "0${selectedScheduleStartHour}" else {selectedScheduleStartHour.toString()} + ":" +
                          if(selectedScheduleStartMinute<10) "0$selectedScheduleStartMinute" else selectedScheduleStartMinute.toString()

        val endString = if(selectedScheduleEndHour<10) "0$selectedScheduleEndHour" else {selectedScheduleEndHour.toString()} + ":" +
                if(selectedScheduleEndMinute<10) "0$selectedScheduleEndMinute" else selectedScheduleEndMinute.toString()

        selectedScheduleString.postValue("$startString - $endString")
    }

    fun getScheduleTimeString(): LiveData<String> = selectedScheduleString

    fun setSelectedLimitNumberMode(mode: LimitNumberMode) = selectedLimitNumberMode.postValue(mode)

    fun setSelectedLimitNumber(limit: Int) = selectedLimitNumber.postValue(limit)

    fun getLimitNumber(): LiveData<Int> =  selectedLimitNumber

    fun getLimitNumberMode(): LiveData<LimitNumberMode> = selectedLimitNumberMode

    fun getSelectedApplications(): LiveData<ArrayList<SelectApplicationsFragment.SelectAppListItem>> = selectedApplications

    fun getCurrentSteps(): LiveData<Int> = currentStep

    fun getEnableNextButton(): LiveData<Boolean> = enableNextButton

    fun getSelectedBreakTimeString(): LiveData<String> = selectedBreakTimeString

    fun stepForward() {
        currentStep.postValue(currentStep.value?.plus(1))
    }

    fun stepBackwards() {
        currentStep.postValue(currentStep.value?.minus(1))
    }

    fun addAppToList(app: String) {
        selectedApplications.value?.find { it.packageName.equals(app) }?.selected = true
        selectedApplications.postValue(selectedApplications.value)
        selectedApplications.value?.let { updatePrevButtonStep1(it) }
    }

    fun removeAppFromList(app: String) {
        selectedApplications.value?.find { it.packageName.equals(app) }?.selected = false
        selectedApplications.postValue(selectedApplications.value)
        selectedApplications.value?.let { updatePrevButtonStep1(it) }
    }

    fun getSelectedRuleTypeList(): LiveData<ArrayList<RuleTypeListItem>> = selectedRuleTypeList

    fun setSelectedRuleTypeInList(ruleType: RuleType) {
        selectedRuleTypeList.value?.forEach{
            it.selected = false
        }
        selectedRuleTypeList.value?.get(Utils.ruleTypeToUIPosition(ruleType))?.selected = true
        selectedRuleType = ruleType
        selectedRuleTypeList.postValue(selectedRuleTypeList.value)
    }

    private fun updateBreakTimeString(){
        val hourString = NotificationManagerApplication.appContext.resources.getString(R.string.hour)
        val hoursString = NotificationManagerApplication.appContext.resources.getString(R.string.hours)
        val minuteString = NotificationManagerApplication.appContext.resources.getString(R.string.minute)
        val minutesString = NotificationManagerApplication.appContext.resources.getString(R.string.minutes)

        if (selectedBreakTimeMinutes == 0 && selectedBreakTimeHours == 0) {
            selectedBreakTimeString.postValue("No Time Selected")
        } else if(selectedBreakTimeHours == 0){
            selectedBreakTimeString.postValue("$selectedBreakTimeMinutes ${if(selectedBreakTimeMinutes==1) minuteString else minutesString}")
        }else if (selectedBreakTimeMinutes==0){
            selectedBreakTimeString.postValue("$selectedBreakTimeHours ${if(selectedBreakTimeHours==1) hourString else hoursString}")
        }else{
            selectedBreakTimeString.postValue("$selectedBreakTimeHours " +
                    "${if(selectedBreakTimeHours==1) hourString else hoursString} and $selectedBreakTimeMinutes ${if(selectedBreakTimeMinutes==1) minuteString else minutesString}")
        }

        NotificationManagerApplication.appContext.resources.getString(R.string.minute)
        NotificationManagerApplication.appContext.resources.getString(R.string.minutes)
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
        result.add(RuleTypeListItem(RuleType.SHORT_BREAK, true))
        result.add(RuleTypeListItem(RuleType.SCHEDULE, false))
        result.add(RuleTypeListItem(RuleType.LIMIT_NUMBER, false))
        result.add(RuleTypeListItem(RuleType.ETERNALLY, false))
        return result
    }

}

enum class RuleType { ETERNALLY, LIMIT_NUMBER, SHORT_BREAK, SCHEDULE }

enum class LimitNumberMode { DAY, WEEK, HOUR, NOT_SELECTED }

enum class Weekdays { MO, TU, WE, TH, FR, SA, SU }



