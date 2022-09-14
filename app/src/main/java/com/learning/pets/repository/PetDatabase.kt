package com.learning.pets.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.learning.pets.bl.CommonUtil.PET_DATABASE
import com.learning.pets.model.IPetListDao
import com.learning.pets.model.Pet

/**
 * @author Anita
 * The following code defines an AppDatabase class to hold the database of Pets.
 */
@Database(entities = [Pet::class], version = 1, exportSchema = false)
abstract class PetDatabase : RoomDatabase() {

    abstract fun petDao(): IPetListDao

    companion object {
        @Volatile
        private var INSTANCE: PetDatabase? = null
        fun getDatabaseClient(context: Context): PetDatabase {
            if (INSTANCE != null) return INSTANCE!!
            synchronized(this)
            {
                INSTANCE = Room.databaseBuilder(context, PetDatabase::class.java, PET_DATABASE)
                    .fallbackToDestructiveMigration().build()
                return INSTANCE!!
            }

        }
    }
}