package com.odroid.naviproject.network

import com.odroid.naviproject.model.PullRequest
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubPullRequestsApi {

    @GET("pulls")
    suspend fun getPullRequests(@Query("state") state: String): Response<List<PullRequest>>
}