package com.sheniv.iksone.helpers

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.room.Room
import com.sheniv.iksone.room.UserDatabase
import com.sheniv.iksone.room.UserDatabaseDao

lateinit var db : UserDatabaseDao

fun initRoomDatabase(context: Context) {
    val databaseDao = Room.databaseBuilder(
        context,
        UserDatabase::class.java,
        "users"
    ).allowMainThreadQueries().build()
    db = databaseDao.todoDao()
}

fun View.beVisible() {
    this.visibility = View.VISIBLE
}

fun View.beGone() {
    this.visibility = View.GONE
}

fun View.beInvisible() {
    this.visibility = View.INVISIBLE
}

fun Fragment.showToast(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}