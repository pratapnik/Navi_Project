package com.odroid.naviproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.odroid.naviproject.model.PullRequest
import com.odroid.naviproject.model.Resource
import com.odroid.naviproject.repository.GithubPullRequestsRepository
import kotlinx.coroutines.launch

class MainActivityViewModel : ViewModel() {

    private val pullRequests = MutableLiveData<Resource<List<PullRequest>>>()

    init {
        fetchInitialClosedPullRequests()
    }

    private fun fetchInitialClosedPullRequests() {
        pullRequests.postValue(Resource.loading(null))

        viewModelScope.launch {
            try {
                val closedPullRequests = GithubPullRequestsRepository.getInitialClosedPullRequests()
                if (closedPullRequests != null) {
                    pullRequests.postValue(Resource.success(closedPullRequests))
                } else {
                    pullRequests.postValue(Resource.error(null))
                }
            } catch (e: Exception) {
                pullRequests.postValue(Resource.error(null))
            }

        }
    }

    fun fetchMorePullRequests() {
        viewModelScope.launch {
            try {
                val closedPullRequests = GithubPullRequestsRepository.getNextClosedPullRequests()
                if (closedPullRequests != null) {
                    pullRequests.postValue(Resource.success(closedPullRequests))
                }
            } catch (e: Exception) {
            }
        }
    }

    fun getPullRequests(): LiveData<Resource<List<PullRequest>>> {
        return pullRequests
    }
}