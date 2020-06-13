package com.example.notificationmanager.detoxRules

import com.example.notificationmanager.ViewModels.RuleType
import java.util.*

class ScheduleRule : DetoxRule() {

    var mm1: Int = -1
    var hh1: Int = -1
    var mm2: Int = -1
    var hh2: Int = -1
    var mo: Boolean = false
    var tu: Boolean = false
    var we: Boolean = false
    var th: Boolean = false
    var fr: Boolean = false
    var sa: Boolean = false
    var su: Boolean = false

    /**
     * @param hh1 start hour
     * @param mm1 start minute
     * @param hh2 end hour
     * @param mm2 end minute
     * @param mo if app is forbidden on this day
     * @param tu if app is forbidden on this day
     * @param we if app is forbidden on this day
     * @param th if app is forbidden on this day
     * @param fr if app is forbidden on this day
     * @param sa if app is forbidden on this day
     * @param su if app is forbidden on this day
     */

    override fun fire(packageName: String): Boolean {
        return if (this.packageName == packageName) {
            val start = hh1 * 60 + mm1
            val end = hh2 * 60 + mm2
            val hh: Int = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
            val mm: Int = Calendar.getInstance().get(Calendar.MINUTE)
            val curr = hh * 60 + mm
            val dow: Int = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
            if (start <= end) {
                // normal case when start time and end time are in the same day
                when (dow) {
                    Calendar.MONDAY -> mo && timeCheck(start, end, curr)
                    Calendar.TUESDAY -> tu && timeCheck(start, end, curr)
                    Calendar.WEDNESDAY -> we && timeCheck(start, end, curr)
                    Calendar.THURSDAY -> th && timeCheck(start, end, curr)
                    Calendar.FRIDAY -> fr && timeCheck(start, end, curr)
                    Calendar.SATURDAY -> sa && timeCheck(start, end, curr)
                    Calendar.SUNDAY -> su && timeCheck(start, end, curr)
                    else -> false
                }
            } else {
                // overnight rule: start time is bigger than endtime,
                // so we assume endtime simply belongs to the next day
                if (curr >= start) {
                    //curr is before midnight and more than start
                    when (dow) {
                        Calendar.MONDAY -> mo
                        Calendar.TUESDAY -> tu
                        Calendar.WEDNESDAY -> we
                        Calendar.THURSDAY -> th
                        Calendar.FRIDAY -> fr
                        Calendar.SATURDAY -> sa
                        Calendar.SUNDAY -> su
                        else -> false
                    }
                } else if (curr <= end) {
                    //curr is after midnight and before end
                    when (dow) {
                        Calendar.MONDAY -> su
                        Calendar.TUESDAY -> mo
                        Calendar.WEDNESDAY -> tu
                        Calendar.THURSDAY -> we
                        Calendar.FRIDAY -> th
                        Calendar.SATURDAY -> fr
                        Calendar.SUNDAY -> sa
                        else -> false
                    }
                } else  //curr is simply not in the interval start - end
                    false
            }
        } else {
            false
        }
    }

    fun getRemainingTimeInMs(): Int {
        val start = hh1 * 60 + mm1
        val end = hh2 * 60 + mm2
        val hh = Calendar.getInstance()[Calendar.HOUR_OF_DAY]
        val mm = Calendar.getInstance()[Calendar.MINUTE]
        val curr = hh * 60 + mm
        val dow = Calendar.getInstance()[Calendar.DAY_OF_WEEK]
        return if (start <= end) {
            // normal case when start time and end time are in the same day
            (end - curr) * 60 * 1000
        } else {
            // overnight rule: start time is bigger than endtime,
            // so we assume endtime simply belongs to the next day
            if (curr >= start) {
                //curr is before midnight and more than start
                (end + (24 * 60 - curr)) * 60 * 1000
            } else if (curr <= end) {
                //curr is after midnight and before end
                (end - curr) * 60 * 1000
            } else  //curr is simply not in the interval start - end
                0
        }
    }

    override fun getRuleType(): RuleType {
        return RuleType.SCHEDULE
    }

    private fun timeCheck(start: Int, end: Int, curr: Int): Boolean {
        return start <= curr && curr <= end
    }

    private fun getDaysAsArray(): BooleanArray? {
        return booleanArrayOf(mo, tu, we, th, fr, sa, su)
    }


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (!super.equals(other)) return false
        if (javaClass != other.javaClass) return false
        val other: ScheduleRule = other as ScheduleRule
        if (fr !== other.fr) return false
        if (hh1 !== other.hh1) return false
        if (hh2 !== other.hh2) return false
        if (mm1 !== other.mm1) return false
        if (mm2 !== other.mm2) return false
        if (mo !== other.mo) return false
        if (sa !== other.sa) return false
        if (su !== other.su) return false
        if (th !== other.th) return false
        if (tu !== other.tu) return false
        return if (we !== other.we) false else true
    }

    override fun hashCode(): Int {
        val prime = 31;
        var result: Int = super.hashCode();
        result = prime * result + if (fr) 1231 else 1237
        result = prime * result + hh1;
        result = prime * result + hh2;
        result = prime * result + mm1;
        result = prime * result + mm2;
        result = prime * result + if (mo) 1231 else 1237
        result = prime * result + if (sa) 1231 else 1237
        result = prime * result + if (su) 1231 else 1237
        result = prime * result + if (th) 1231 else 1237
        result = prime * result + if (tu) 1231 else 1237
        result = prime * result + if (we) 1231 else 1237
        return result;
    }

    companion object {
        @JvmStatic
        fun isOverNight(
            fromHour: Int,
            fromMinute: Int,
            toHour: Int,
            toMinute: Int
        ): Boolean {
            return fromHour * 60 + fromMinute > toHour * 60 + toMinute
        }
    }
}