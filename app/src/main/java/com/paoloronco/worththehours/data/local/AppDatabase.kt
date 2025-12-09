package com.paoloronco.worththehours.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.paoloronco.worththehours.model.Item

@Database(entities = [Item::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun itemDao(): ItemDao
}
