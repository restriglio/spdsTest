package com.testspds.api

import com.testspds.api.response.PostsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PostsApi {

    @GET("rest/v1/freshly-pressed")
    fun getPosts(@Query("number") number: Int): Call<PostsResponse>

}