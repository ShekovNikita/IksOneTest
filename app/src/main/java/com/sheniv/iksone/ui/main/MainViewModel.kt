package com.sheniv.iksone.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.GsonBuilder
import com.sheniv.iksone.helpers.db
import com.sheniv.iksone.model.Person
import com.sheniv.iksone.retrofit.Retrofit
import com.sheniv.iksone.retrofit.RetrofitCreator
import com.sheniv.iksone.room.UserRoom
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val gson = GsonBuilder().serializeNulls().create()
    private val api = RetrofitCreator().createService(gson, Retrofit::class.java)

    private var _age = MutableLiveData<Person>()
    val age: LiveData<Person> = _age

    fun getAge(name: String) {
        viewModelScope.launch(Dispatchers.Main) {
            val response = api.getAge(name)
            if (response.isSuccessful) {
                response.body()?.let {
                    _age.postValue(it)
                }
            }
        }
    }

    fun insertUser(userRoom: UserRoom) = db.insert(userRoom)

    fun addToFavorite(name: String) = db.addToFavorites(name)

    fun getAllUsers() = db.getAllUsers()
}