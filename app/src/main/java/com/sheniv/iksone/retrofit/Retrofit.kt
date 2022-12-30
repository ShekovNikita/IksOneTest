package com.sheniv.iksone.retrofit

import com.sheniv.iksone.model.Person
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Retrofit {

    @GET(".")
    suspend fun getAge(@Query("name") name: String): Response<Person>
}