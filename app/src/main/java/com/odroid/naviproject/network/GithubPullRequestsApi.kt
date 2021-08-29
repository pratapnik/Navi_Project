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