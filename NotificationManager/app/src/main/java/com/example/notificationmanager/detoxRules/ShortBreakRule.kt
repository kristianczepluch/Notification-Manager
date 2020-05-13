package com.example.notificationmanager.detoxRules

import com.example.notificationmanager.ViewModels.RuleType
import com.example.notificationmanager.utils.Utils


class ShortBreakRule(var timestampEnd: Long = 0) : DetoxRule() {

    override fun fire(packageName: String) = this.packageName.equals(packageName) && (Utils.getCurrentTime() < timestampEnd)

    override fun getRuleType() = RuleType.SHORT_BREAK

    override fun hashCode(): Int {
        val prime = 31
        var result = super.hashCode()
        result = prime * result + (timestampEnd xor (timestampEnd ushr 32)).toInt()
        return result
    }

    fun getRemainingTimeInMs() = timestampEnd - Utils.getCurrentTime()


    override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (!super.equals(other)) return false
            if (javaClass != other.javaClass) return false
            other as ShortBreakRule
            return timestampEnd == other.timestampEnd
    }
}