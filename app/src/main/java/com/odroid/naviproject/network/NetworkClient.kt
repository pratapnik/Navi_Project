package com.odroid.naviproject.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkClient {
    private const val baseUrl = "https://api.github.com/repos/pratapnik/Psyche/"

    private val networkClient: Retrofit.Builder by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
    }

    val GITHUB_PULL_REQUEST_API: GithubPullRequestApi by lazy {
        networkClient.build().create(GithubPullRequestApi::class.java)
    }
}