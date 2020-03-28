package com.example.notificationmanager

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(NotificationEntity::class), version = 1)
abstract class NotificationsDatabase : RoomDatabase() {

    abstract fun getNotificationDao () : NotificationDao

    companion object {
        @Volatile
        private var INSTANCE: NotificationsDatabase? = null

        fun getDatabase(context: Context) : NotificationsDatabase {
            val tmpInstance = INSTANCE
            if(tmpInstance != null) return tmpInstance
            else{
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

}