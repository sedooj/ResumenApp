package com.sedooj.resumen.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.sedooj.resumen.storage.entity.AuthUserEntity

@Dao
interface AuthUserDao {
    @Query("SELECT * FROM  AuthUserEntity")
    fun getAuthorizationData(): AuthUserEntity

    @Insert
    fun insert(auth: AuthUserEntity)

    @Update
    fun update(auth: AuthUserEntity)
}