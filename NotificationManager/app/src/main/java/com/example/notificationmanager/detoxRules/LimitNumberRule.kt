package com.example.notificationmanager.detoxRules

import android.app.Application
import com.example.notificationmanager.ViewModels.LimitNumberMode
import com.example.notificationmanager.ViewModels.RuleType
import com.example.notificationmanager.data.DetoxRuleEntity
import com.example.notificationmanager.data.Repository
import com.example.notificationmanager.utils.Utils
import java.util.*


class LimitNumberRule(
    val application: Application
) : DetoxRule() {

    var allowedNumberOfLaunches: Int = -1
    var timeSlotType: Int = -1
    var timeslotID: Int = -1
    var launchesSoFar: Int = 0
    var currentTimeSlotId: Int = -2
    val repository: Repository = Repository(application)

    override fun fire(packageName: String): Boolean {
        if (packageName == this.packageName) {
            updateTimeSlotId()
            launchesSoFar++
            updateDatabase()
            return launchesSoFar > allowedNumberOfLaunches
        } else return false
    }

    override fun getRuleType() = RuleType.LIMIT_NUMBER

    fun getRemainingLaunches(): Int {
        updateTimeSlotId()
        if (currentTimeSlotId != timeslotID) updateDatabase()
        return allowedNumberOfLaunches - launchesSoFar
    }

    private fun updateTimeSlotId() {
        when (Utils.timeSlotTypeToLimitNumberMode(timeSlotType)) {
            LimitNumberMode.DAY -> {
                currentTimeSlotId = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
            }
            LimitNumberMode.HOUR -> {
                currentTimeSlotId =
                    Calendar.getInstance().get(Calendar.DAY_OF_WEEK) * 1000 + Calendar.getInstance()
                        .get(Calendar.HOUR_OF_DAY)
            }
            LimitNumberMode.WEEK -> {
                currentTimeSlotId = Calendar.getInstance().get(Calendar.WEEK_OF_YEAR)
            }
            else -> throw Exception("Timeslottype not found")
        }

        if (currentTimeSlotId != timeslotID) {
            timeslotID = currentTimeSlotId
            launchesSoFar = 0
        }
    }

    private fun updateDatabase() {
        val updatedRule = DetoxRuleEntity(
            ruleType = Utils.ruleTypeToUIPosition(getRuleType()),
            packageName = this.packageName,
            appName = this.appName,
            ruleLimitNumberAllowedNumber = this.allowedNumberOfLaunches,
            ruleLimitNumberLaunchesSoFar = launchesSoFar,
            ruleLimitNumberTimeSlotType = timeSlotType,
            ruleLimitNumberTimeSlotID = currentTimeSlotId
        )
        updatedRule.id = this._id
        repository.updateDetoxRule(updatedRule)
    }
}