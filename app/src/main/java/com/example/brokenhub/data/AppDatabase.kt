package com.example.brokenhub.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// ✅ Add all your entities here
@Database(
    entities = [User::class, Listing::class],
    version = 2,
    exportSchema = false   // disables schema export warning
)
abstract class AppDatabase : RoomDatabase() {

    // ✅ Declare all DAOs
    abstract fun userDao(): UserDao
    abstract fun listingDao(): ListingDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                    // ✅ Auto‑wipe and rebuild if schema changes (safe for dev, not prod)
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}
