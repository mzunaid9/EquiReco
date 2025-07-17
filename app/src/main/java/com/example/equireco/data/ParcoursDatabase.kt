package com.example.equireco.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Parcours::class], version = 1, exportSchema = false)
abstract class ParcoursDatabase : RoomDatabase() {
    abstract fun parcoursDao(): ParcoursDao

    companion object {
        @Volatile
        private var INSTANCE: ParcoursDatabase? = null

        fun getDatabase(context: Context): ParcoursDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ParcoursDatabase::class.java,
                    "parcours_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
