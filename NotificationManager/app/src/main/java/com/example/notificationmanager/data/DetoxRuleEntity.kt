package com.example.notificationmanager.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.notificationmanager.utils.Utils

@Entity(tableName = "detoxRules_table")
class DetoxRuleEntity(
    val ruleType: Int,
    val packageName: String,
    val appName: String,
    val deleted: Boolean = false,
    val appGone: Boolean = false,
    val active: Boolean = true,
    val created: Long = Utils.getCurrentTime(),
    val ruleBreakTimeEndTimestamp: Long = -1,
    val ruleScheduleStartMinute: Int = -1,
    val ruleScheduleStartHour: Int = -1,
    val ruleScheduleEndMinute: Int = -1,
    val ruleScheduleEndHour: Int = -1,
    val ruleScheduleMO: Boolean = false,
    val ruleScheduleTU: Boolean = false,
    val ruleScheduleWE: Boolean = false,
    val ruleScheduleTH: Boolean = false,
    val ruleScheduleFR: Boolean = false,
    val ruleScheduleSA: Boolean = false,
    val ruleScheduleSU: Boolean = false,
    val ruleLimitNumberTimeSlotType: Int = -1,
    val ruleLimitNumberAllowedNumber: Int = -1,
    val ruleLimitNumberTimeSlotID: Int = -1,
    val ruleLimitNumberLaunchesSoFar: Int = 0
) {
    public @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
