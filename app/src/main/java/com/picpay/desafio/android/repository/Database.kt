package com.picpay.desafio.android.repository

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.picpay.desafio.android.model.User

@androidx.room.Database(entities = [User::class], version = 1)
abstract class Database: RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: Database? = null

        fun getDatabase(context: Context): Database {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    Database::class.java,
                    "user_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}