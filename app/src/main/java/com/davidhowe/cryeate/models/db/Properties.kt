package com.davidhowe.cryeate.models.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "PropertiesTable")
class Properties(
    @PrimaryKey var uid: Int,
    @ColumnInfo(name = "api_last_active") var apiLastActive: Long = 0L,
    @ColumnInfo(name = "api_prices_last_retrieved") var apiPricesLastRetrieved: Long = 0L
)