package com.antarikshc.stockplay.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.antarikshc.stockplay.models.Stock

@Database(entities = [Stock::class], version = 1)
abstract class StockDatabase : RoomDatabase() {

    abstract fun stockDao(): StockDao

    companion object {
        /**
         * INSTANCE will keep a reference to any database returned via getInstance.
         *
         * This will help us avoid repeatedly initializing the database, which is expensive.
         *
         *  The value of a volatile variable will never be cached, and all writes and
         *  reads will be done to and from the main memory. It means that changes made by one
         *  thread to shared data are visible to other threads.
         */
        @Volatile
        private var INSTANCE: StockDatabase? = null

        /**
         * Helper function to get the database.
         * @param context The application context Singleton, used to get access to the filesystem.
         */
        fun getInstance(context: Context): StockDatabase {
            // Multiple threads can ask for the database at the same time, ensure we only initialize
            // it once by using synchronized. Only one thread may enter a synchronized block at a
            // time.
            synchronized(this) {
                // Copy the current value of INSTANCE to a local variable so Kotlin can smart cast.
                // Smart cast is only available to local variables.
                var instance = INSTANCE
                // If instance is `null` make a new database instance.
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        StockDatabase::class.java,
                        "stock_play_database"
                    )
                        // Wipes and rebuilds instead of migrating if no Migration object.
                        .fallbackToDestructiveMigration()
                        .build()
                    // Assign INSTANCE to the newly created database.
                    INSTANCE = instance
                }
                // Return instance; smart cast to be non-null.
                return instance
            }
        }
    }

}