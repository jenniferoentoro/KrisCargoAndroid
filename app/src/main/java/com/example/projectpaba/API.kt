package com.example.projectpaba

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface API {
    @Headers("Accept: application/json")
    @POST("/api/auth/login")
    fun login(@Body requestBody: RequestBody): Call<ResponseBody>

    @Headers("Accept: application/json")
    @GET("/api/auth/profile")
    fun getProfile(@Header("Authorization") bearerToken: String) : Call<ResponseBody>

    @Headers("Accept: application/json")
    @POST("/api/auth/refresh")
    fun refresh(@Header("Authorization") bearerToken: String) : Call<ResponseBody>

    @Headers("Accept: application/json")
    @POST("/api/auth/logout")
    fun logout(@Header("Authorization") bearerToken: String) : Call<ResponseBody>

    @Headers("Accept: application/json")
    @POST("/api/auth/register")
    fun register(@Body requestBody: RequestBody) : Call<ResponseBody>

}