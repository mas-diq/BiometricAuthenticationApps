package com.masdiq.biometricauthenticationapps

import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    // Update one data
    @FormUrlEncoded
    @PUT("api/posts/{id}")
    fun putPost(
        @Path("id") id: String?,
        @Field("name") name: String?,
        @Field("address") address: String?,
        @Field("age") age: String?,
    ): Call<UserResponse>
//
//    // Get one data
//    @GET("api/posts")
//    fun getPost(
//        @Path("id") id: String?,
//        @Field("title") title: String?,
//        @Field("content") subtitle: String?,
//        @Field("place") place: String?,
//    ): Call<UserResponse>
}