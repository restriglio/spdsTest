package com.testspds

import androidx.lifecycle.MutableLiveData
import com.testspds.api.ApiResponse
import com.testspds.api.PostsApi
import com.testspds.api.RetrofitBuilder
import com.testspds.api.response.PostsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostsRepository {

    private val postsResponse: MutableLiveData<ApiResponse<PostsResponse?>> = MutableLiveData()
    private var postsApi: PostsApi = RetrofitBuilder().provideUsersService()

    fun requestPosts(postsPaging: Int) {
        val request = postsApi.getPosts(postsPaging)
        request.enqueue(object : Callback<PostsResponse> {

            override fun onResponse(call: Call<PostsResponse>, response: Response<PostsResponse>) {
                postsResponse.postValue(
                    ApiResponse(
                        response.isSuccessful,
                        response.body(),
                        response.message()
                    )
                )
            }

            override fun onFailure(call: Call<PostsResponse>, t: Throwable) {
                postsResponse.postValue(
                    ApiResponse(
                        false,
                        null,
                        t.message!!
                    )
                )
            }

        })
    }

    fun getPostsResponse() = postsResponse

}