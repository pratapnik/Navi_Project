package com.odroid.naviproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.odroid.naviproject.model.PullRequest
import com.odroid.naviproject.model.Resource
import com.odroid.naviproject.repository.GithubPullRequestsRepository
import kotlinx.coroutines.launch

class MainActivityViewModel: ViewModel() {

    private val pullRequests = MutableLiveData<Resource<List<PullRequest>>>()

    init {
        fetchClosedPullRequests()
    }

    private fun fetchClosedPullRequests() {
        pullRequests.postValue(Resource.loading(null))

        viewModelScope.launch {
            try {
                val closedPullRequests = GithubPullRequestsRepository.getClosedPullRequests()
                if(!closedPullRequests.isNullOrEmpty()) {
                    pullRequests.postValue(Resource.success(closedPullRequests))
                } else
                    pullRequests.postValue(Resource.error(null))
            } catch (e:Exception) {
                pullRequests.postValue(Resource.error(null))
            }

        }
    }

    fun getPullRequests(): LiveData<Resource<List<PullRequest>>> {
        return pullRequests
    }
}