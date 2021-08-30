package com.odroid.naviproject.repository

import com.odroid.naviproject.model.PullRequest
import com.odroid.naviproject.network.NetworkClient
import com.odroid.naviproject.util.Constants
import com.odroid.naviproject.util.DateUtil

object GithubPullRequestsRepository {

    private var pageIndex = 1

    suspend fun getInitialClosedPullRequests(): List<PullRequest>? {
        return getClosedPullRequests()
    }

    suspend fun getNextClosedPullRequests(): List<PullRequest>? {
        pageIndex++
        return getClosedPullRequests()
    }

    private suspend fun getClosedPullRequests(): List<PullRequest>? {
        val response = NetworkClient.githubPullRequestsApi.getPullRequests(
            Constants.PULL_REQUEST_STATE,
            Constants.PULL_REQUESTS_SORT, pageIndex
        )
        if (response.isSuccessful && response.body()?.isEmpty() == true) {
            return emptyList()
        }
        return response.body()?.let { getUpdatedList(it) }
    }

    private fun getUpdatedList(list: List<PullRequest>): List<PullRequest> {
        val pullRequestList = arrayListOf<PullRequest>()
        for (pullRequest in list) {
            val createdAt = getRequiredDate(pullRequest.createdAt)
            val closedAt = getRequiredDate(pullRequest.closedAt)
            val newPullRequest =
                PullRequest(pullRequest.title, pullRequest.user, createdAt, closedAt)
            pullRequestList.add(newPullRequest)
        }
        return pullRequestList
    }

    private fun getRequiredDate(time: String?): String {
        return time?.let {
            DateUtil.getFormattedDate(
                it, Constants.GITHUB_DATE_FORMAT,
                Constants.MONTH_NAME_DATE_FORMAT
            )
        } ?: ""
    }
}