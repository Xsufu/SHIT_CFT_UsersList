package com.example.shitcftuserslist.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 1, exportSchema = false )
abstract class UserRoomDatabase: RoomDatabase() {
    // Получение Dao
    abstract fun userDao(): UserDao
    companion object {
        /**
         * Переменная сохраняет ссылку на БД.
         *
         * Аннотация говорит, что переменная не кэшируется
         * и все записи и чтения будут выполняться в основную память.
         * Изменения, внесенные одним потоком в [INSTANCE], видны всем другим потокам.
         */
        @Volatile
        private var INSTANCE: UserRoomDatabase? = null
        fun getDatabase(context: Context): UserRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserRoomDatabase::class.java,
                    "user_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE  = instance
                return instance
            }
        }
    }
}