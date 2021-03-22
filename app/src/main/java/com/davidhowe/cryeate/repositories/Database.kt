package com.davidhowe.cryeate.repositories

import androidx.room.Database
import androidx.room.RoomDatabase
import com.davidhowe.cryeate.models.db.Coin
import com.davidhowe.cryeate.models.db.Properties

@Database(entities = [Properties::class, Coin::class], version = 1)
abstract class Database : RoomDatabase() {
    abstract fun propertiesDao(): PropertiesDao
    abstract fun coinDao(): CoinDao
}