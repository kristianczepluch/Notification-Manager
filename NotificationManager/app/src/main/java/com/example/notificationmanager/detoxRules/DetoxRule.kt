package com.example.notificationmanager.detoxRules

import com.example.notificationmanager.R
import com.example.notificationmanager.ViewModels.RuleType

abstract class DetoxRule
    (
    var deleted: Long = 0,
    var appGone: Int = 0,
    var active: Int = 0,
    var packageName: String = "",
    var appName: String = "",
    var created: Long = 0
) {

    var _id: Int = -1

    abstract fun fire(packageName: String): Boolean

    abstract fun getRuleType(): RuleType


    enum class TimeSlotType{
        TIMESLOT_TYPE_MINUTE,
        TIMESLOT_TYPE_HOUR,
        TIMESLOT_TYPE_DAY,
        TIMESLOT_TYPE_WEEK,
        TIMESLOT_TYPE_NONE
    }

    fun getForThisTimeslotStringRessource(timeslotType: TimeSlotType): Int{
        return when(timeslotType){
            TimeSlotType.TIMESLOT_TYPE_MINUTE -> R.string.rule_timeslot_for_this_minute
            TimeSlotType.TIMESLOT_TYPE_HOUR -> R.string.rule_timeslot_for_this_hour
            TimeSlotType.TIMESLOT_TYPE_DAY -> R.string.rule_timeslot_for_this_day
            TimeSlotType.TIMESLOT_TYPE_WEEK -> R.string.rule_timeslot_for_this_week
            TimeSlotType.TIMESLOT_TYPE_NONE -> R.string.rule_timeslot_none
        }
    }

    override fun equals(other: Any?): Boolean {

        if(other == null) return false
        if(other == this) return true
        if (javaClass != other.javaClass) return false;

        other as DetoxRule

        if (!_id.equals(other._id))
            return false;
        else if (!packageName.equals(other.packageName)) {
            return false
        } else if(!appName.equals(other.appName)){
            return false
        }
        if(deleted != other.deleted) return false;
        if(created != other.created) return false;
        if(appGone != other.appGone) return false;
        if(active != other.active) return false;
        return true;
    }

    override fun hashCode(): Int {
        val prime = 31
        var result = 1
        result = prime * result + _id.hashCode()
        result = (prime * result + packageName.hashCode())
        result = (prime * result + appName.hashCode())
        result = prime * result + java.lang.Long.valueOf(created).hashCode()
        result = prime * result + java.lang.Long.valueOf(deleted).hashCode()
        result = prime * result + Integer.valueOf(appGone).hashCode()
        result = prime * result + Integer.valueOf(active).hashCode()
        return result
    }
}