package com.odroid.naviproject.network

import com.odroid.naviproject.model.PullRequest
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface GithubPullRequestsApi {

    @Headers("Authorization: token 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")
    @GET("pulls")
    suspend fun getPullRequests(
        @Query("state") state: String,
        @Query("sort") sort: String,
        @Query("page") pageNumber: Int
    ): Response<List<PullRequest>>
}