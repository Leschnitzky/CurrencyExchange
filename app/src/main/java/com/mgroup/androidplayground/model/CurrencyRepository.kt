package com.mgroup.androidplayground.model

import kotlinx.coroutines.flow.Flow

interface CurrencyRepository {
    suspend fun getAmountMultiplier(typeFrom : String, typeTo: String): Flow<String?>
    fun sendLogsToEmail()
}