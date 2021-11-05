package com.example.arditsarja.sliide.model

import com.example.arditsarja.sliide.user.User
import com.google.gson.annotations.SerializedName



data class ResponseCreate(
        @SerializedName("meta")
    val meta: Meta,
        @SerializedName("data")
    val data: User
)
