package com.sheniv.iksone.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDatabaseDao {

    @Insert
    fun insert(userRoom: UserRoom)

    @Query("SELECT * from users")
    fun getAllUsers(): List<UserRoom>

    @Query("SELECT * FROM users WHERE favorite = 1")
    fun getFavorites(): List<UserRoom>

    @Query("UPDATE users SET favorite = 1 WHERE name =:name")
    fun addToFavorites(name: String)

    @Query("UPDATE users SET favorite = 0 WHERE id IN (:idList)")
    fun deleteFromFavorites(idList: List<Int>)
}