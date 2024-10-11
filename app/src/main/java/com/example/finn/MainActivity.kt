package com.example.finn

import android.animation.ObjectAnimator
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var stockViewModel: StockViewModel
    private lateinit var priceViewModel: PriceViewModel
    private lateinit var progressBar: ProgressBar
    private lateinit var symbol: EditText
    private lateinit var price: TextView
    private lateinit var percent: TextView
    private lateinit var company: TextView
    private lateinit var updateButton: Button
    private lateinit var handler: Handler

    private val apiKey = BuildConfig.API_KEY

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize the handler here
        handler = Handler(Looper.getMainLooper())

        // View references
        progressBar = findViewById(R.id.progressBar)
        symbol = findViewById(R.id.symbolName)
        price = findViewById(R.id.stockPriceTextView)
        percent = findViewById(R.id.perchangeTextView)
        company = findViewById(R.id.companyTextView)
        updateButton = findViewById(R.id.updateButton)

        // Initialize ViewModels
        stockViewModel = ViewModelProvider(this)[StockViewModel::class.java]
        priceViewModel = ViewModelProvider(this)[PriceViewModel::class.java]

        setupObservers()

        updateButton.setOnClickListener {
            val symbolString = symbol.text.toString()
            if (symbolString.isNotEmpty()) {
                stockViewModel.fetchStockQuote(symbolString, apiKey, createRetrofitService())
                priceViewModel.fetchPrice(symbolString, apiKey, createRetrofitService1())
            } else {
                Toast.makeText(this, "Please enter a valid symbol", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupObservers() {
        stockViewModel.isLoading.observe(this, Observer { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        })

        stockViewModel.stockQuote.observe(this, Observer { stockQuote ->
            percent.text = stockQuote.percentChange
            priceViewModel.stockPrice.observe(this, Observer { priceData ->
                price.text = priceData.price
            })
            company.text = stockQuote.name

            updateStockPrice(
                (percent.text as String).toDouble()
            )
        })

        stockViewModel.errorMessage.observe(this, Observer { error ->
            if (error != null) {
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
            }
        })
    }

    // Method to create Retrofit service
    private fun createRetrofitService(): TwelveDataApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.twelvedata.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(TwelveDataApiService::class.java)
    }

    // Method to create another Retrofit service for price
    private fun createRetrofitService1(): OneDataApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.twelvedata.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(OneDataApiService::class.java)
    }

    private fun updateStockPrice( percentChange: Double) {
        handler.post {
            startBlinkAnimation(percent)

            percent.text = String.format("%.2f", percentChange)
            val textColor = if (percentChange >= 0.00) Color.GREEN else Color.RED
            percent.setTextColor(textColor)
        }
    }

    private fun startBlinkAnimation(textView: TextView) {
        val blink = ObjectAnimator.ofFloat(textView, "alpha", 1f, 0f, 1f)
        blink.duration = 200
        blink.start()
    }
}
