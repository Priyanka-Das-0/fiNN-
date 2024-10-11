package com.example.finn

import com.google.gson.annotations.SerializedName

data class StockQuoteResponse(
    @SerializedName("symbol")
    val symbol: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("exchange")
    val exchange: String,

    @SerializedName("mic_code")
    val micCode: String,

    @SerializedName("currency")
    val currency: String,

    @SerializedName("datetime")
    val datetime: String,

    @SerializedName("timestamp")
    val timestamp: Long,

    @SerializedName("open")
    val open: String,

    @SerializedName("high")
    val high: String,

    @SerializedName("low")
    val low: String,

    @SerializedName("close")
    val close: String,

    @SerializedName("volume")
    val volume: String,

    @SerializedName("previous_close")
    val previousClose: String,

    @SerializedName("change")
    val change: String,

    @SerializedName("percent_change")
    val percentChange: String,

    @SerializedName("average_volume")
    val averageVolume: String,

    @SerializedName("is_market_open")
    val isMarketOpen: Boolean,

    @SerializedName("fifty_two_week")
    val fiftyTwoWeek: FiftyTwoWeek
)

data class FiftyTwoWeek(
    @SerializedName("low")
    val low: String,

    @SerializedName("high")
    val high: String,

    @SerializedName("low_change")
    val lowChange: String,

    @SerializedName("high_change")
    val highChange: String,

    @SerializedName("low_change_percent")
    val lowChangePercent: String,

    @SerializedName("high_change_percent")
    val highChangePercent: String,

    @SerializedName("range")
    val range: String
)
data class PriceResponse(
    @SerializedName("price")
    val price: String
)

