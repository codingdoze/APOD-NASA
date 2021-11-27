package com.goldmedal.hrapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import wale.nasa.apod.data.db.PictureOfTheDayDao
import wale.nasa.apod.data.db.entities.PictureOfTheDay


@Database(
        version = 1,
    entities = [PictureOfTheDay::class],
        exportSchema = true
)


abstract class AppDatabase : RoomDatabase() {

    abstract fun getPhotoOfTheDayDao(): PictureOfTheDayDao


    companion object {

        @Volatile
        private var instance: AppDatabase? = null
        private val LOCK = Any()


        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "room-database.db")
                  .fallbackToDestructiveMigration()
                    .build()
    }
}