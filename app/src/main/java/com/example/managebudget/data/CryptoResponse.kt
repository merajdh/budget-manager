package com.example.managebudget.data


import com.google.gson.annotations.SerializedName

data class CryptoResponse(
    @SerializedName("Data")
    val `data`: List<Data>,
) {
    data class Data(
        @SerializedName("CoinInfo")
        val coinInfo: CoinInfo,
        @SerializedName("RAW")
        val rAW: RAW
    ) {
        data class CoinInfo(
            @SerializedName("FullName")
            val fullName: String,
            @SerializedName("Id")
            val id: String,
            @SerializedName("ImageUrl")
            val imageUrl: String,
            @SerializedName("Name")
            val name: String,
        ) {

        }

    }

    data class RAW(
        @SerializedName("USD")
        val uSD: USD
    ) {
        data class USD(
            @SerializedName("CHANGEPCT24HOUR")
            val cHANGEPCT24HOUR: Double,
            @SerializedName("PRICE")
            val pRICE: Double,
        )
    }
}


