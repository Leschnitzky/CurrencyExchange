package com.mgroup.androidplayground.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface CurrencyService {
    @GET("currency-api@1/2021-12-24/currencies/{currency}.json")
    fun getCurrencies(@Path("currency") currencyType: String?): Call<String?>
}