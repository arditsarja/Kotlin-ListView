package com.example.arditsarja.sliide.user

import com.google.gson.annotations.SerializedName

data class UserCreate(
    @SerializedName("name")
    val name: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("gender")
    val gender: String?,
    @SerializedName("status")
    val status: String?

)