package com.example.mvvmdemo.data.db.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mvvmdemo.data.db.dao.QuoteDao
import com.example.mvvmdemo.data.db.dao.UserDao
import com.example.mvvmdemo.data.db.entities.Quote
import com.example.mvvmdemo.data.db.entities.User

@Database(
    entities = [User::class, Quote::class],
    version = 1
)

abstract class AppDatabase : RoomDatabase() {

    abstract fun getUserDao () : UserDao
    abstract fun getQuoteDao () : QuoteDao

    companion object{

        @Volatile // This variable is immediately visible to all the threads
        private var instance : AppDatabase? = null

        private val LOCK = Any()  // TO make sure We don't create two instances of our database

        operator fun invoke(context: Context) = instance
            ?: synchronized(LOCK){
            instance
                ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "MyDatabase.db"
            ).build()
    }
}