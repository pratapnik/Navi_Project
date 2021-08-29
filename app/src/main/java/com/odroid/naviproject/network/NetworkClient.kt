package com.odroid.naviproject.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkClient {
    const val baseUrl = "https://api.github.com/repos/quasarframework/quasar/"

    private val networkClient: Retrofit.Builder by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
    }

    val githubPullRequestsApi: GithubPullRequestsApi by lazy {
        networkClient.build().create(GithubPullRequestsApi::class.java)
    }
}