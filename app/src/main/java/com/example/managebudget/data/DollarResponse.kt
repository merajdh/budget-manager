package com.example.managebudget.data


import com.google.gson.annotations.SerializedName

data class DollarResponse(
    @SerializedName("d")
    val d: String,
    @SerializedName("dt")
    val dt: String,
    @SerializedName("p")
    val p: String,
    @SerializedName("t")
    val t: String,
    @SerializedName("t-g")
    val tG: String,
    @SerializedName("ts")
    val ts: String
)