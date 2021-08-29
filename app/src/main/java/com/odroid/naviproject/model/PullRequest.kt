package com.odroid.naviproject.model

import com.google.gson.annotations.SerializedName

data class PullRequest(
    @SerializedName("title")
    val title: String? = "",
    @SerializedName("user")
    val user: User? = User(),
    @SerializedName("created_at")
    val createdAt: String? = "",
    @SerializedName("closed_at")
    val closedAt: String? = "",
)
