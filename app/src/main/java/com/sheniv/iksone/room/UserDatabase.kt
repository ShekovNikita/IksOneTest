package com.sheniv.iksone.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    exportSchema = true,
    version = 1,
    entities = [UserRoom::class],
    /*autoMigrations = [
        AutoMigration (from = 1, to = 2)
    ]*/
)
abstract class UserDatabase : RoomDatabase() {

    abstract fun todoDao(): UserDatabaseDao
}