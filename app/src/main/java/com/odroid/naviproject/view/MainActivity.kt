package com.odroid.naviproject.view

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.odroid.naviproject.R
import com.odroid.naviproject.model.PullRequest
import com.odroid.naviproject.model.Status
import com.odroid.naviproject.util.Constants
import com.odroid.naviproject.viewmodel.MainActivityViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var mainActivityViewModel: MainActivityViewModel
    private var pullRequestAdapter: PullRequestAdapter? = null
    private var prRecyclerView: RecyclerView? = null
    private var progressBar: ProgressBar? = null
    private var titleView: TextView? = null

    private val isLastPage = false
    private val isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        prRecyclerView = findViewById(R.id.rvPullRequests)
        progressBar = findViewById(R.id.progressBar)
        titleView = findViewById(R.id.tv_pull_request_title)

        setupAdapter()
        setupViewModel()
        setupObserver()
    }

    private fun setupAdapter() {
        prRecyclerView?.layoutManager = LinearLayoutManager(this)
        pullRequestAdapter = PullRequestAdapter(arrayListOf())
        prRecyclerView?.addItemDecoration(
            DividerItemDecoration(
                prRecyclerView?.context,
                (prRecyclerView?.layoutManager as LinearLayoutManager).orientation
            )
        )
        prRecyclerView?.adapter = pullRequestAdapter
        prRecyclerView?.setOnScrollListener(recyclerViewOnScrollListener)
    }

    private fun setupViewModel() {
        mainActivityViewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]
    }

    private fun setupObserver() {
        mainActivityViewModel.getPullRequests().observe(this, {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let { it1 -> showSuccessState(it1) }
                }
                Status.LOADING -> {
                    showLoadingState()
                }
                Status.ERROR -> {
                    showErrorState()
                }
            }
        })
    }

    private fun showErrorState() {
        progressBar?.visibility = View.GONE
        Toast.makeText(
            this, resources.getString(R.string.label_error_message),
            Toast.LENGTH_LONG
        ).show()
        titleView?.visibility = View.GONE
    }

    private fun showLoadingState() {
        progressBar?.visibility = View.VISIBLE
        titleView?.visibility = View.VISIBLE
        prRecyclerView?.visibility = View.GONE
    }

    private fun showSuccessState(pullRequests: List<PullRequest>) {
        progressBar?.visibility = View.GONE
        prRecyclerView?.visibility = View.VISIBLE
        titleView?.visibility = View.VISIBLE
        renderList(pullRequests)
    }

    private fun renderList(pullRequests: List<PullRequest>) {
        pullRequestAdapter?.addData(pullRequests)
        pullRequestAdapter?.notifyDataSetChanged()
    }

    private var recyclerViewOnScrollListener: RecyclerView.OnScrollListener? =
        object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visibleItemCount: Int =
                    (prRecyclerView?.layoutManager as LinearLayoutManager).childCount
                val totalItemCount: Int =
                    (prRecyclerView?.layoutManager as LinearLayoutManager).itemCount
                val firstVisibleItemPosition: Int =
                    (prRecyclerView?.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                if (!isLoading && !isLastPage) {
                    if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0 &&
                        totalItemCount >= Constants.PAGE_SIZE
                    ) {
                        mainActivityViewModel.fetchMorePullRequests()
                    }
                }
            }
        }

    override fun onDestroy() {
        recyclerViewOnScrollListener = null
        pullRequestAdapter = null
        super.onDestroy()
    }
}