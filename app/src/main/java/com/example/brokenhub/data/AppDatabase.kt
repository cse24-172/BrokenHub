package com.example.brokenhub.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// Room database for BrokenHub.
// It stores ListingEntity objects and exposes ListingDao for queries.
@Database(
    entities = [ListingEntity::class], // Add more entities here if needed
    version = 1,
    exportSchema = true // keep schema for migrations
)
abstract class AppDatabase : RoomDatabase() {

    // DAO access
    abstract fun listingDao(): ListingDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        // Singleton instance
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "brokenhub_db"
                )
                    // Add migrations here if you bump version later
                    .fallbackToDestructiveMigration() // wipes data if schema changes
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
