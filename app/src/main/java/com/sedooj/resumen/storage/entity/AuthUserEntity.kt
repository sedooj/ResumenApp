package com.sedooj.resumen.storage.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AuthUserEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "username") val username: String,
    @ColumnInfo(name = "password") val password: String,
)
