package com.peppo.githubapp.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.peppo.githubapp.model.response.DetailUserResponse

@Database(entities = [DetailUserResponse::class], version = 1)
abstract class FavouriteRoomDatabase: RoomDatabase() {
    abstract fun favDao(): FavDao

    companion object {
        @Volatile
        private var INSTANCE: FavouriteRoomDatabase? = null
        @JvmStatic
        fun getDatabase(context: Context): FavouriteRoomDatabase {
            if (INSTANCE == null) {
                synchronized(FavouriteRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        FavouriteRoomDatabase::class.java, "favourite_database")
                        .build()
                }
            }
            return INSTANCE as FavouriteRoomDatabase
        }
    }
}