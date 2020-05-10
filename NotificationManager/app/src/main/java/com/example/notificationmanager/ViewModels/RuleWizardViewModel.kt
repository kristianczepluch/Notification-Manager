package com.example.notificationmanager.ViewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.notificationmanager.R
import com.example.notificationmanager.fragments.ruleCreation.SelectApplicationsFragment
import com.example.notificationmanager.utils.NotificationManagerApplication
import com.example.notificationmanager.utils.Utils

class RuleWizardViewModel(application: Application) : AndroidViewModel(application) {

    var selectedWeekdayCheckboxes: BooleanArray = BooleanArray(7)

    var selectedScheduleStartMinute: Int = 0
    var selectedScheduleEndMinute: Int = 0
    var selectedScheduleStartHour: Int = 0
    var selectedScheduleEndHour: Int = 0

    private val selectedWeekdays: MutableLiveData<ArrayList<Weekdays>> = MutableLiveData(ArrayList())

    private val selectedScheduleString = MutableLiveData("00:00 - 00:00")

    var selectedBreakTimeMinutes = 0
    var selectedBreakTimeHours = 0
    private val selectedBreakTimeString = MutableLiveData("0 Minutes")

    var selectedRuleType= MutableLiveData(RuleType.SHORT_BREAK)

    private val selectedLimitNumberMode = MutableLiveData(LimitNumberMode.DAY)
    private val selectedLimitNumber = MutableLiveData(1)

    private val currentStep = MutableLiveData(0)

    private val selectedApplications: MutableLiveData<ArrayList<SelectApplicationsFragment.SelectAppListItem>> = MutableLiveData(createDefaultAppList())

    private val enableNextButton: MutableLiveData<Boolean> = MutableLiveData(false)

    private val enableCreateButton: MutableLiveData<Boolean> = MutableLiveData(false)

    fun getWeekdays(): LiveData<ArrayList<Weekdays>> = selectedWeekdays

    fun getEnableCreateButton(): LiveData<Boolean> = enableCreateButton

    fun addWeekDay(weekday: Weekdays, position: Int) {
        selectedWeekdays.value?.add(weekday)
        selectedWeekdayCheckboxes[position] = true
        selectedWeekdays.postValue(selectedWeekdays.value)
        updateNextButtonScheduleWeekdayFragment()
    }

    fun removeWeekDay(weekday: Weekdays, position: Int) {
        selectedWeekdays.value?.remove(weekday)
        selectedWeekdayCheckboxes[position] = false
        selectedWeekdays.postValue(selectedWeekdays.value)
        updateNextButtonScheduleWeekdayFragment()
    }



    // All Getter Methodes for the RuleWizardUI

    fun setSelectedBreakTimeHour(hour: Int) {
        selectedBreakTimeHours = hour
        updateBreakTimeString()
        updateNextButtonShortBreakFragment()
    }

    fun setSelectedBreakTimeMinute(min: Int) {
        selectedBreakTimeMinutes = min
        updateBreakTimeString()
        updateNextButtonShortBreakFragment()
    }

    fun setScheduleStartHour(startHour: Int){
        selectedScheduleStartHour = startHour
        updateScheduleTimeString()
        updateNextButtonScheduleTime()
    }

    fun setScheduleStartMinute(startMinute: Int){
        selectedScheduleStartMinute = startMinute
        updateScheduleTimeString()
        updateNextButtonScheduleTime()
    }

    fun setScheduleEndHour(endHour: Int){
        selectedScheduleEndHour = endHour
        updateScheduleTimeString()
        updateNextButtonScheduleTime()
    }

    fun setScheduleEndMinute(endMinute: Int){
        selectedScheduleEndMinute = endMinute
        updateScheduleTimeString()
        updateNextButtonScheduleTime()
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
        (currentStep.value?.plus(1))?.let { updateNavigationFragment(it) }
        currentStep.postValue(currentStep.value?.plus(1))
    }

    fun stepBackwards() {
        (currentStep.value?.minus(1))?.let { updateNavigationFragment(it) }
        currentStep.postValue(currentStep.value?.minus(1))
    }

    fun addAppToList(app: String) {
        selectedApplications.value?.find { it.packageName.equals(app) }?.selected = true
        selectedApplications.postValue(selectedApplications.value)
        selectedApplications.value?.let { updateNextButtonStep1(it) }
    }

    fun removeAppFromList(app: String) {
        selectedApplications.value?.find { it.packageName.equals(app) }?.selected = false
        selectedApplications.postValue(selectedApplications.value)
        selectedApplications.value?.let { updateNextButtonStep1(it) }
    }

    fun setSelectedRuleType(ruleType: RuleType) { selectedRuleType.postValue(ruleType) }

    private fun updateBreakTimeString(){
        val hourString = NotificationManagerApplication.appContext.resources.getString(R.string.hour)
        val hoursString = NotificationManagerApplication.appContext.resources.getString(R.string.hours)
        val minuteString = NotificationManagerApplication.appContext.resources.getString(R.string.minute)
        val minutesString = NotificationManagerApplication.appContext.resources.getString(R.string.minutes)

        if(selectedBreakTimeHours == 0){
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


    private fun updateNextButtonStep1(appList: ArrayList<SelectApplicationsFragment.SelectAppListItem>) {
        var atLeastOneAppSelected = false

        appList.forEach {
            if (it.selected) {
                atLeastOneAppSelected = true
            }
        }
        if (atLeastOneAppSelected) {
            enableNextButton.postValue(true)
        }
        else {
            enableNextButton.postValue(false)
        }

    }

    private fun updateNextButtonShortBreakFragment(){
        if(selectedBreakTimeHours == 0 && selectedBreakTimeMinutes == 0){
            enableNextButton.postValue(false)
        } else {
            enableNextButton.postValue(true)
        }
    }

    private fun updateNextButtonSelectRuleFragment(){ enableNextButton.postValue(true) }

    private fun updateNextButtonScheduleWeekdayFragment(){
        if(selectedWeekdays.value?.isEmpty()!!) enableNextButton.postValue(false)
        else enableNextButton.postValue(true)
    }

    private fun updateNextButtonScheduleTime(){
        val start = selectedScheduleStartHour * 60 + selectedScheduleStartMinute
        val end = selectedScheduleEndHour * 60 + selectedScheduleEndMinute
        if ( start+end==0 ) enableNextButton.postValue(false)
        else enableNextButton.postValue(true)
    }

    private fun updateNavigationFragment(step: Int){
        when(step){
            0 -> updateNextButtonStep1(selectedApplications.value!!)
            1 -> updateNextButtonSelectRuleFragment()
            2 -> {
                if(selectedRuleType.value == RuleType.SHORT_BREAK) updateNextButtonShortBreakFragment()
                if(selectedRuleType.value == RuleType.SCHEDULE) updateNextButtonScheduleWeekdayFragment()
                if(selectedRuleType.value == RuleType.LIMIT_NUMBER) updateNextButtonLimitNumberFragment()
                if(selectedRuleType.value == RuleType.ETERNALLY) activateCreateButton()
            }
            3 -> {
                if(selectedRuleType.value == RuleType.SHORT_BREAK) activateCreateButton()
                if(selectedRuleType.value == RuleType.LIMIT_NUMBER) activateCreateButton()
                if(selectedRuleType.value == RuleType.SCHEDULE) updateNextButtonScheduleTime()
            }
            4 -> if(selectedRuleType.value == RuleType.SCHEDULE) activateCreateButton()
        }
    }

    private fun activateCreateButton(){
        enableCreateButton.postValue(true)
    }

    private fun deactiveCreateButton(){
        enableCreateButton.postValue(false)
    }

    private fun updateNextButtonLimitNumberFragment(){
        enableNextButton.postValue(true)
    }

    fun createDefaultAppList(): ArrayList<SelectApplicationsFragment.SelectAppListItem> {
        val dataList = ArrayList<SelectApplicationsFragment.SelectAppListItem>()
        Utils.getAllInstalledApps().forEach {
            dataList.add(SelectApplicationsFragment.SelectAppListItem(it, false))
        }
        return dataList
    }
}

enum class RuleType { ETERNALLY, LIMIT_NUMBER, SHORT_BREAK, SCHEDULE, NOT_SELECTED }

enum class LimitNumberMode { DAY, WEEK, HOUR, NOT_SELECTED }

enum class Weekdays { MO, TU, WE, TH, FR, SA, SU }



