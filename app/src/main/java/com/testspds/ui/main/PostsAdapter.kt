package com.testspds.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.testspds.R
import com.testspds.api.response.PostsResponse

class PostsAdapter(private val posts: List<PostsResponse.Posts>, private val context: Context) :
    RecyclerView.Adapter<PostsAdapter.PostsViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsViewHolder {
        return PostsViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.post_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    override fun onBindViewHolder(holder: PostsViewHolder, position: Int) {
        val post = posts[position]
        holder.title.text = post.title
        Picasso.get().load(post.featuredImage)
            .placeholder(
                context.getDrawable(R.drawable.ic_launcher_background)!!
            ).resize(600, 600)
            .centerCrop()
            .into(holder.image)
    }

    class PostsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.post_title)
        val image: ImageView = itemView.findViewById(R.id.post_image)
    }
}