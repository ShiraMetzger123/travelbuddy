package com.project.travelbuddy.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CountryEntity::class], version = 1)
abstract class TravelBuddyDatabase : RoomDatabase() {

    abstract fun countryDao(): CountryDao

    companion object {
        @Volatile
        private var INSTANCE: TravelBuddyDatabase? = null

        fun getDatabase(context: Context): TravelBuddyDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TravelBuddyDatabase::class.java,
                    "travel_buddy_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}