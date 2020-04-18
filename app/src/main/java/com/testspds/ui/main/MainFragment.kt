package com.testspds.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.testspds.PostsRepository
import com.testspds.R
import com.testspds.api.response.PostsResponse
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainFragment : Fragment() {

    private lateinit var rootView: View
    private val postsPaging = 30
    private var repository: PostsRepository = PostsRepository()

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        rootView = inflater.inflate(R.layout.main_fragment, container, false)
        return rootView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestPosts()
    }

    override fun onResume() {
        super.onResume()
        repository.getPostsResponse().observe(this, Observer { result ->
            if (result.isSuccessful) {
                loadPosts(result.response!!)
                return@Observer
            }

            showError(result.error)
        })
    }

    private fun requestPosts() {
        GlobalScope.launch {
            context?.let {
                repository.requestPosts(postsPaging)
            }
        }
    }

    private fun showError(error: String) {
        progress_container.visibility = GONE
        Snackbar.make(
            rootView,
            error,
            Snackbar.LENGTH_SHORT
        ).show()
    }

    private fun filterPostsWithFeatureImage(posts: List<PostsResponse.Posts>) = posts.filter {
        it.featuredImage.isNotEmpty()
    }


    private fun loadPosts(postsResponse: PostsResponse) {
        val posts = filterPostsWithFeatureImage(postsResponse.posts)
        val adapter = PostsAdapter(posts, context!!)
        posts_lists.layoutManager = LinearLayoutManager(context)
        posts_lists.adapter = adapter
        progress_container.visibility = GONE
    }

    override fun onPause() {
        super.onPause()
        repository.getPostsResponse().removeObservers(this as LifecycleOwner)
    }
}
