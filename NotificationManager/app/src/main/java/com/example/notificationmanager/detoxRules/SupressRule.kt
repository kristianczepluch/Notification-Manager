package com.example.notificationmanager.detoxRules

import com.example.notificationmanager.ViewModels.RuleType

class SupressRule : DetoxRule() {

    override fun fire(packageName: String) = this.packageName == packageName

    override fun getRuleType() = RuleType.ETERNALLY

}