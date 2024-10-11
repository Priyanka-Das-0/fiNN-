
package com.example.finn
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TwelveDataApiService {

    @GET("quote")
    fun getStockQuote(
        @Query("symbol") symbol: String,
        @Query("apikey") apiKey: String
    ): Call<StockQuoteResponse>
}
interface OneDataApiService {

    @GET("price")
    fun getPrice(
        @Query("symbol") symbol: String,
        @Query("apikey") apiKey: String
    ): Call<PriceResponse>
}
