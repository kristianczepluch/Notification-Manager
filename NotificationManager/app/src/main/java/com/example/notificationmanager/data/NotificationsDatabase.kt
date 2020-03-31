package com.example.notificationmanager.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@Database(entities = arrayOf(NotificationEntity::class), version = 1, exportSchema = false)
abstract class NotificationsDatabase : RoomDatabase() {

    abstract fun getNotificationDao () : NotificationDao

    companion object {
        @Volatile
        private var INSTANCE: NotificationsDatabase? = null
        private const val NUMBER_OF_THREADS = 4
        val databaseWriteExecutor: ExecutorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS)

        fun getDatabase(context: Context) : NotificationsDatabase {
            val tmpInstance = INSTANCE
            if(tmpInstance != null) return tmpInstance
            synchronized(this){
                    val instance = Room.databaseBuilder(context.applicationContext,
                        NotificationsDatabase::class.java,
                        "notification_database").fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                    return instance
            }

        }

    }

}