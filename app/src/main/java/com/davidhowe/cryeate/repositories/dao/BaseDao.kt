/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.davidhowe.cryeate.repositories.dao

import androidx.room.*
import io.reactivex.Completable


interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(obj: T?) : Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(obj: List<T>?) : Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg obj: T) : Completable

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(obj: T)  : Completable

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(obj: List<T>?) : Completable

    @Delete
    fun delete(obj: T) : Completable
}