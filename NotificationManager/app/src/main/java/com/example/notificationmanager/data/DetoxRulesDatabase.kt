package com.example.notificationmanager.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@Database(entities = arrayOf(DetoxRuleEntity::class), version = 1, exportSchema = false)
abstract class DetoxRulesDatabase : RoomDatabase() {

    abstract fun getDetoxRuleDao () : DetoxRulesDao

    companion object {
        @Volatile
        private var INSTANCE: DetoxRulesDatabase? = null
        private const val NUMBER_OF_THREADS = 2
        val databaseWriteExecutor: ExecutorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS)

        fun getDatabase(context: Context) : DetoxRulesDatabase {
            val tmpInstance = INSTANCE
            if(tmpInstance != null) return tmpInstance
            synchronized(this){
                val instance = Room.databaseBuilder(context.applicationContext,
                    DetoxRulesDatabase::class.java,
                    "detoxRules_database").fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }

        }

    }

}