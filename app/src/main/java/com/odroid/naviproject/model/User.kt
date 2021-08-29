package com.odroid.naviproject.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("login")
    val userName: String? = "",
    @SerializedName("avatar_url")
    val photoUrl: String? = ""
)
