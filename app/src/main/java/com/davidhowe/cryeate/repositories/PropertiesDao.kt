package com.davidhowe.cryeate.repositories

import androidx.room.Dao
import androidx.room.Query
import com.davidhowe.cryeate.models.db.Properties
import io.reactivex.Single

@Dao
interface PropertiesDao : BaseDao<Properties> {
    @Query("SELECT * FROM Properties")
    fun getEntry(): Single<Properties>
}