package com.example.notificationmanager.ViewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.notificationmanager.R
import com.example.notificationmanager.data.DetoxRuleEntity
import com.example.notificationmanager.data.Repository
import com.example.notificationmanager.fragments.ruleCreation.SelectApplicationsFragment
import com.example.notificationmanager.utils.NotificationManagerApplication
import com.example.notificationmanager.utils.Utils
import java.util.concurrent.TimeUnit

class RuleWizardViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: Repository = Repository(application)

    var selectedWeekdayCheckboxes: BooleanArray = BooleanArray(7)

    var selectedScheduleStartMinute: Int = 0
    var selectedScheduleEndMinute: Int = 0
    var selectedScheduleStartHour: Int = 0
    var selectedScheduleEndHour: Int = 0

    private val selectedWeekdays: MutableLiveData<ArrayList<Weekdays>> =
        MutableLiveData(ArrayList())

    private val selectedScheduleString = MutableLiveData("00:00 - 00:00")

    var selectedBreakTimeMinutes = 0
    var selectedBreakTimeHours = 0
    private val selectedBreakTimeString = MutableLiveData("0 Minutes")

    var selectedRuleType = MutableLiveData(RuleType.SHORT_BREAK)

    private val selectedLimitNumberMode = MutableLiveData(LimitNumberMode.DAY)
    private val selectedLimitNumber = MutableLiveData(1)

    private val currentStep = MutableLiveData(0)

    private val selectedApplications: MutableLiveData<ArrayList<SelectApplicationsFragment.SelectAppListItem>> =
        MutableLiveData(createDefaultAppList())

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

    fun setScheduleStartHour(startHour: Int) {
        selectedScheduleStartHour = startHour
        updateScheduleTimeString()
        updateNextButtonScheduleTime()
    }

    fun setScheduleStartMinute(startMinute: Int) {
        selectedScheduleStartMinute = startMinute
        updateScheduleTimeString()
        updateNextButtonScheduleTime()
    }

    fun setScheduleEndHour(endHour: Int) {
        selectedScheduleEndHour = endHour
        updateScheduleTimeString()
        updateNextButtonScheduleTime()
    }

    fun setScheduleEndMinute(endMinute: Int) {
        selectedScheduleEndMinute = endMinute
        updateScheduleTimeString()
        updateNextButtonScheduleTime()
    }

    private fun updateScheduleTimeString() {

        val startString = if (selectedScheduleStartHour < 10) "0${selectedScheduleStartHour}" else {
            selectedScheduleStartHour.toString()
        } + ":" +
                if (selectedScheduleStartMinute < 10) "0$selectedScheduleStartMinute" else selectedScheduleStartMinute.toString()

        val endString = if (selectedScheduleEndHour < 10) "0$selectedScheduleEndHour" else {
            selectedScheduleEndHour.toString()
        } + ":" +
                if (selectedScheduleEndMinute < 10) "0$selectedScheduleEndMinute" else selectedScheduleEndMinute.toString()

        selectedScheduleString.postValue("$startString - $endString")
    }

    fun getScheduleTimeString(): LiveData<String> = selectedScheduleString

    fun setSelectedLimitNumberMode(mode: LimitNumberMode) = selectedLimitNumberMode.postValue(mode)

    fun setSelectedLimitNumber(limit: Int) = selectedLimitNumber.postValue(limit)

    fun getLimitNumber(): LiveData<Int> = selectedLimitNumber

    fun getLimitNumberMode(): LiveData<LimitNumberMode> = selectedLimitNumberMode

    fun getSelectedApplications(): LiveData<ArrayList<SelectApplicationsFragment.SelectAppListItem>> =
        selectedApplications

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

    fun setSelectedRuleType(ruleType: RuleType) {
        selectedRuleType.postValue(ruleType)
    }

    private fun updateBreakTimeString() {
        val hourString =
            NotificationManagerApplication.appContext.resources.getString(R.string.hour)
        val hoursString =
            NotificationManagerApplication.appContext.resources.getString(R.string.hours)
        val minuteString =
            NotificationManagerApplication.appContext.resources.getString(R.string.minute)
        val minutesString =
            NotificationManagerApplication.appContext.resources.getString(R.string.minutes)

        if (selectedBreakTimeHours == 0) {
            selectedBreakTimeString.postValue("$selectedBreakTimeMinutes ${if (selectedBreakTimeMinutes == 1) minuteString else minutesString}")
        } else if (selectedBreakTimeMinutes == 0) {
            selectedBreakTimeString.postValue("$selectedBreakTimeHours ${if (selectedBreakTimeHours == 1) hourString else hoursString}")
        } else {
            selectedBreakTimeString.postValue(
                "$selectedBreakTimeHours " +
                        "${if (selectedBreakTimeHours == 1) hourString else hoursString} and $selectedBreakTimeMinutes ${if (selectedBreakTimeMinutes == 1) minuteString else minutesString}"
            )
        }
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
        } else {
            enableNextButton.postValue(false)
        }

    }

    private fun updateNextButtonShortBreakFragment() {
        if (selectedBreakTimeHours == 0 && selectedBreakTimeMinutes == 0) {
            enableNextButton.postValue(false)
        } else {
            enableNextButton.postValue(true)
        }
    }

    private fun updateNextButtonSelectRuleFragment() {
        enableNextButton.postValue(true)
    }

    private fun updateNextButtonScheduleWeekdayFragment() {
        if (selectedWeekdays.value?.isEmpty()!!) enableNextButton.postValue(false)
        else enableNextButton.postValue(true)
    }

    private fun updateNextButtonScheduleTime() {
        val start = selectedScheduleStartHour * 60 + selectedScheduleStartMinute
        val end = selectedScheduleEndHour * 60 + selectedScheduleEndMinute
        if (start + end == 0) enableNextButton.postValue(false)
        else enableNextButton.postValue(true)
    }

    private fun updateNavigationFragment(step: Int) {
        when (step) {
            0 -> updateNextButtonStep1(selectedApplications.value!!)
            1 -> updateNextButtonSelectRuleFragment()
            2 -> {
                if (selectedRuleType.value == RuleType.SHORT_BREAK) updateNextButtonShortBreakFragment()
                if (selectedRuleType.value == RuleType.SCHEDULE) updateNextButtonScheduleWeekdayFragment()
                if (selectedRuleType.value == RuleType.LIMIT_NUMBER) updateNextButtonLimitNumberFragment()
                if (selectedRuleType.value == RuleType.ETERNALLY) activateCreateButton()
            }
            3 -> {
                if (selectedRuleType.value == RuleType.SHORT_BREAK) activateCreateButton()
                if (selectedRuleType.value == RuleType.LIMIT_NUMBER) activateCreateButton()
                if (selectedRuleType.value == RuleType.SCHEDULE) updateNextButtonScheduleTime()
            }
            4 -> if (selectedRuleType.value == RuleType.SCHEDULE) activateCreateButton()
        }
    }

    private fun activateCreateButton() {
        enableCreateButton.postValue(true)
    }

    private fun updateNextButtonLimitNumberFragment() {
        enableNextButton.postValue(true)
    }

    fun createDefaultAppList(): ArrayList<SelectApplicationsFragment.SelectAppListItem> {
        val dataList = ArrayList<SelectApplicationsFragment.SelectAppListItem>()
        Utils.getAllInstalledApps().forEach {
            dataList.add(SelectApplicationsFragment.SelectAppListItem(it, false))
        }
        return dataList
    }


    fun saveDetoxRule() {
        when (selectedRuleType.value) {

            RuleType.ETERNALLY -> {
                selectedApplications.value?.forEach {
                    if (it.selected) {
                        val ruleType = RuleType.ETERNALLY
                        val packageName = it.packageName
                        val appName = Utils.getAppNameFromPackageName(it.packageName)

                        repository.insertDetoxRule(
                            DetoxRuleEntity(
                                ruleType = Utils.ruleTypeToUIPosition(ruleType),
                                packageName = packageName,
                                appName = appName
                            )
                        )
                    }
                }
            }

            RuleType.SHORT_BREAK -> {
                selectedApplications.value?.forEach {

                    if (it.selected) {

                        val ruleType = RuleType.SHORT_BREAK
                        val packageName = it.packageName
                        val appName = Utils.getAppNameFromPackageName(it.packageName)
                        val breakTimeEndTimestamp =
                            Utils.getCurrentTime() + TimeUnit.HOURS.toMillis(
                                selectedBreakTimeMinutes.toLong()
                            ) + TimeUnit.MINUTES.toMillis(
                                selectedBreakTimeMinutes.toLong()
                            )

                        repository.insertDetoxRule(
                            DetoxRuleEntity(
                                ruleType = Utils.ruleTypeToUIPosition(ruleType),
                                packageName = packageName,
                                appName = appName,
                                ruleBreakTimeEndTimestamp = breakTimeEndTimestamp
                            )
                        )
                    }
                }
            }

            RuleType.LIMIT_NUMBER -> {
                selectedApplications.value?.forEach {

                    if (it.selected) {

                        val limitNumber = getLimitNumber().value
                        val limitNumberMode = getLimitNumberMode().value
                        val ruleType = RuleType.LIMIT_NUMBER
                        val packageName = it.packageName
                        val appName = Utils.getAppNameFromPackageName(it.packageName)

                        if (limitNumber != null && limitNumberMode != null) {

                            repository.insertDetoxRule(
                                DetoxRuleEntity(
                                    ruleType = Utils.ruleTypeToUIPosition(ruleType),
                                    packageName = packageName,
                                    appName = appName,
                                    ruleLimitNumberAllowedNumber = limitNumber,
                                    ruleLimitNumberLaunchesSoFar = 0,
                                    ruleLimitNumberTimeSlotType = Utils.limitNumberModeToTimeSlotType(
                                        limitNumberMode
                                    )
                                )
                            )
                        }
                    }
                }
            }

            RuleType.SCHEDULE -> {
                selectedApplications.value?.forEach {
                    if (it.selected) {
                        val ruleType = RuleType.SCHEDULE
                        val packageName = it.packageName
                        val appName = Utils.getAppNameFromPackageName(it.packageName)

                        val mo = selectedWeekdays.value?.contains(Weekdays.MO) ?: false
                        val tu = selectedWeekdays.value?.contains(Weekdays.TU) ?: false
                        val we = selectedWeekdays.value?.contains(Weekdays.WE) ?: false
                        val th = selectedWeekdays.value?.contains(Weekdays.TH) ?: false
                        val fr = selectedWeekdays.value?.contains(Weekdays.FR) ?: false
                        val sa = selectedWeekdays.value?.contains(Weekdays.SA) ?: false
                        val su = selectedWeekdays.value?.contains(Weekdays.SU) ?: false


                        repository.insertDetoxRule(
                            DetoxRuleEntity(
                                ruleType = Utils.ruleTypeToUIPosition(ruleType),
                                packageName = packageName,
                                appName = appName,
                                ruleScheduleStartHour = selectedScheduleStartHour,
                                ruleScheduleEndHour = selectedScheduleEndHour,
                                ruleScheduleStartMinute = selectedScheduleStartMinute,
                                ruleScheduleEndMinute = selectedScheduleEndMinute,
                                ruleScheduleMO = mo,
                                ruleScheduleTU = tu,
                                ruleScheduleWE = we,
                                ruleScheduleTH = th,
                                ruleScheduleFR = fr,
                                ruleScheduleSA = sa,
                                ruleScheduleSU = su
                            )
                        )
                    }
                }
            }
            else -> {
            }
        }
    }
}

enum class RuleType { ETERNALLY, LIMIT_NUMBER, SHORT_BREAK, SCHEDULE, NOT_SELECTED }

enum class LimitNumberMode { DAY, WEEK, HOUR, NOT_SELECTED }

enum class Weekdays { MO, TU, WE, TH, FR, SA, SU }



