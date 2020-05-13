package com.example.notificationmanager.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DetoxRulesDao {

    @Insert
    fun insertDetoxRule(detoxRule: DetoxRuleEntity)

    @Query("DELETE FROM detoxRules_table")
    fun deleteAllDetoxRules()


    @Query("SELECT * FROM detoxRules_table ")
    fun getAllRules(): LiveData<List<DetoxRuleEntity>>

}