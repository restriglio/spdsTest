package com.testspds.api.response

import com.google.gson.annotations.SerializedName

data class PostsResponse(
    @SerializedName("posts")
    var posts: List<Posts>
) {

    data class Posts(
        @SerializedName("title")
        var title: String,
        @SerializedName("featured_image")
        var featuredImage: String
    )

}