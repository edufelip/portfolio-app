package com.example.portfolio_dio.data.models

import com.google.gson.annotations.SerializedName

class Owner(
    val login: String,
    @SerializedName("avatar_url")
    val avatarURL: String
) {
}