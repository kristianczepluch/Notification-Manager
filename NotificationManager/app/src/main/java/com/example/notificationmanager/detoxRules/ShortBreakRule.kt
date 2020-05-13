package com.example.notificationmanager.detoxRules

import com.example.notificationmanager.ViewModels.RuleType
import com.example.notificationmanager.utils.Utils

class BreakTimeRule(var timestampEnd: Long = 0) : DetoxRule() {
    
    override fun fire(packageName: String) = this.packageName.equals(packageName) && (Utils.getCurrentTime() < timestampEnd)

    override fun getSpecificAttributes(): List<Pair<String, String>> {
        TODO("Not yet implemented")
    }

    override fun getRuleType(): RuleType {
        TODO("Not yet implemented")
    }
}