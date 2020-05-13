package com.example.notificationmanager.detoxRules

import com.example.notificationmanager.ViewModels.RuleType


class LimitNumberRule(val allowedNumberOfLaunches:Int, var timeslotID: TimeSlotType = TimeSlotType.TIMESLOT_TYPE_NONE) : DetoxRule() {

   // Todo: Implement this ruletype

    override fun fire(packageName: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun getRuleType(): RuleType {
        TODO("Not yet implemented")
    }


}