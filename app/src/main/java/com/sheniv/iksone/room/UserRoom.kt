package com.sheniv.iksone.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserRoom(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,

    val age: Int,
    val name: String,
    val favorite: Int
)
