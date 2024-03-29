package com.sedooj.resumen.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.sedooj.resumen.storage.entity.AuthUserEntity

@Dao
interface AuthUserDao {
    @Query("SELECT * FROM  AuthUserEntity")
    suspend fun getAuthorizationData(): AuthUserEntity

    @Insert
    suspend fun insert(auth: AuthUserEntity)

    @Update
    suspend fun update(auth: AuthUserEntity)
}