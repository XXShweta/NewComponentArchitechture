package com.example.newcomponentstructure.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(DogBreed::class),version = 1)
abstract class DogDatabase : RoomDatabase(){

    abstract fun dogDAO(): DogDao

    companion object{
        @Volatile private var instance: DogDatabase?= null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: buildDataBase(context).also{
                instance = it
            }
        }

        private fun buildDataBase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            DogDatabase::class.java,
            "dogDB"
        ).build()

    }
}