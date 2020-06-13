package com.example.notificationmanager.algs

import android.app.Application
import com.example.notificationmanager.ViewModels.RuleType
import com.example.notificationmanager.data.DetoxRuleEntity
import com.example.notificationmanager.data.Repository
import com.example.notificationmanager.detoxRules.*
import com.example.notificationmanager.utils.Utils

class NotificationRuleMachine(val application: Application) {

    private val detoxRules: ArrayList<DetoxRule> = ArrayList()
    private val repository: Repository = Repository(application)

    init {
        repository.getAllRules().observeForever { listOfRules ->
            detoxRules.clear()
            listOfRules.forEach{
                detoxRules.add(entitiyToDetoxRule(it,application))
            }
        }
    }

    fun isNotificationAllowed(packageName: String): Long{

        var leftTimeInMs: Long = 0
        var milis_from_now: Long = 0
        var forbidden_eternally: Long = 0
        var launches_per_time: Long = 0

        detoxRules.forEach{
            if (it.fire(packageName)) {
                if(it.getRuleType() == RuleType.SCHEDULE){
                    val scheduleRule: ScheduleRule = it as ScheduleRule
                    leftTimeInMs = scheduleRule.getRemainingTimeInMs().toLong()
                }
                else if (it.getRuleType() == RuleType.ETERNALLY) {
                    forbidden_eternally = -1
                } else if (it.getRuleType() == RuleType.LIMIT_NUMBER) {
                    launches_per_time = -2
                } else if (it.getRuleType() == RuleType.SHORT_BREAK) {
                    val fMfN: ShortBreakRule = it as ShortBreakRule
                    milis_from_now = fMfN.getRemainingTimeInMs()
                }
            }
        }

        // rules hierarchy: fobit eternally > launches_per_time > time_of_day > milis_from_now
        if(forbidden_eternally== (-1).toLong()){
            return forbidden_eternally
        } else if(launches_per_time== (-2).toLong()){
            return launches_per_time;
        } else if(leftTimeInMs > milis_from_now) return leftTimeInMs;
        else return milis_from_now;
    }

    companion object{

        @JvmStatic
        fun entitiyToDetoxRule(entity: DetoxRuleEntity, application: Application): DetoxRule {
            when(Utils.uiPositionToRuleType(entity.ruleType)){

                RuleType.ETERNALLY -> {
                    val supressRule = SupressRule()
                    supressRule._id = entity.id
                    supressRule.packageName = entity.packageName
                    supressRule.appName = entity.appName
                    supressRule.active = if(entity.active) 1 else 0
                    supressRule.appGone = if(entity.appGone) 1 else 0
                    supressRule.created = entity.created
                    return supressRule
                }

                RuleType.SHORT_BREAK -> {
                    val shortBreakRule: ShortBreakRule = ShortBreakRule()
                    shortBreakRule._id = entity.id
                    shortBreakRule.packageName = entity.packageName
                    shortBreakRule.appName = entity.appName
                    shortBreakRule.active = if(entity.active) 1 else 0
                    shortBreakRule.appGone = if(entity.appGone) 1 else 0
                    shortBreakRule.created = entity.created
                    shortBreakRule.timestampEnd = entity.ruleBreakTimeEndTimestamp
                    return shortBreakRule
                }

                RuleType.LIMIT_NUMBER -> {
                    val limitNumberRule = LimitNumberRule(application)
                    limitNumberRule._id = entity.id
                    limitNumberRule.packageName = entity.packageName
                    limitNumberRule.appName = entity.appName
                    limitNumberRule.active = if(entity.active) 1 else 0
                    limitNumberRule.appGone = if(entity.appGone) 1 else 0
                    limitNumberRule.created = entity.created
                    limitNumberRule.launchesSoFar = entity.ruleLimitNumberLaunchesSoFar
                    limitNumberRule.timeslotID = entity.ruleLimitNumberTimeSlotID
                    limitNumberRule.allowedNumberOfLaunches = entity.ruleLimitNumberAllowedNumber
                    limitNumberRule.timeSlotType = entity.ruleLimitNumberTimeSlotType
                    return limitNumberRule
                }

                RuleType.SCHEDULE -> {
                    val scheduleRule = ScheduleRule()
                    scheduleRule._id = entity.id
                    scheduleRule.packageName = entity.packageName
                    scheduleRule.appName = entity.appName
                    scheduleRule.active = if(entity.active) 1 else 0
                    scheduleRule.appGone = if(entity.appGone) 1 else 0
                    scheduleRule.created = entity.created
                    scheduleRule.hh1 = entity.ruleScheduleStartHour
                    scheduleRule.hh2 = entity.ruleScheduleEndHour
                    scheduleRule.mm1 = entity.ruleScheduleStartMinute
                    scheduleRule.mm2 = entity.ruleScheduleEndMinute
                    scheduleRule.mo = entity.ruleScheduleMO
                    scheduleRule.tu = entity.ruleScheduleTU
                    scheduleRule.we = entity.ruleScheduleWE
                    scheduleRule.th = entity.ruleScheduleTH
                    scheduleRule.fr = entity.ruleScheduleFR
                    scheduleRule.sa = entity.ruleScheduleSA
                    scheduleRule.su = entity.ruleScheduleSU
                    return scheduleRule}
                else -> throw Exception("Ruletype not supported or not found")
            }
        }
    }
}