package com.example.finn
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StockViewModel : ViewModel() {

    private val _stockQuote = MutableLiveData<StockQuoteResponse>()
    val stockQuote: LiveData<StockQuoteResponse> get() = _stockQuote

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    fun fetchStockQuote(symbol: String, apiKey: String, apiService: TwelveDataApiService) {
        _isLoading.value = true
        _errorMessage.value = null

        val call = apiService.getStockQuote(symbol, apiKey)

        call.enqueue(object : Callback<StockQuoteResponse> {
            override fun onResponse(
                call: Call<StockQuoteResponse>,
                response: Response<StockQuoteResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _stockQuote.value = response.body()
                } else {
                    _errorMessage.value = "Error: ${response.code()}"
                }
            }

            override fun onFailure(call: Call<StockQuoteResponse>, t: Throwable) {
                _isLoading.value = false
                _errorMessage.value = t.message
            }
        })
    }
}
