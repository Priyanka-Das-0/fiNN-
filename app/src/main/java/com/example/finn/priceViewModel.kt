package com.example.finn

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PriceViewModel : ViewModel() {

    private val _stockQuote = MutableLiveData<PriceResponse>()
    val stockPrice: LiveData<PriceResponse> get() = _stockQuote

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    fun fetchPrice(symbol: String, apiKey: String, apiService: OneDataApiService) {
        _isLoading.value = true
        _errorMessage.value = null

        val call = apiService.getPrice(symbol, apiKey)

        call.enqueue(object : Callback<PriceResponse> {
            override fun onResponse(
                call: Call<PriceResponse>,
                response: Response<PriceResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _stockQuote.value = response.body()
                } else {
                    _errorMessage.value = "Error: ${response.code()}"
                }
            }

            override fun onFailure(call: Call<PriceResponse>, t: Throwable) {
                _isLoading.value = false
                _errorMessage.value = t.message
            }
        })
    }
}
