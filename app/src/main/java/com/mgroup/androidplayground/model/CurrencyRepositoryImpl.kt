package com.mgroup.androidplayground.model

import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.await
import timber.log.Timber
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(
    private val currencyService: CurrencyService,
    private val workManager: WorkManager
) : CurrencyRepository {
    override suspend fun getAmountMultiplier(typeFrom: String, typeTo: String): Flow<String?> {
        return flow {
            Timber.d("Types : $typeFrom -> $typeTo")
            try{
                val jsonScope = currencyService.getCurrencies(
                    typeFrom
                ).await()
                val list = parseJsonString(jsonScope,typeTo!!)
                emit(list.first { currencyEntry ->
                    currencyEntry.type.contains(typeTo)
                }.currencyMultiplier)
            } catch(e: Exception){
                Timber.e(e.localizedMessage)
            }
        }
    }

    private fun parseJsonString(jsonString: String?, typeTo: String): List<CurrencyEntry> {
        val toReturn = arrayListOf<CurrencyEntry>()
        jsonString!!
            .split("\n")
            .drop(3).dropLast(2)
            .filter {
                it.isNotEmpty()
            }.map {
                it.trim()
            }.forEach {
                str ->
                val entry = str.split(":")
                val type = entry[0]
                var currency = entry[1].trim()
                if(currency.endsWith(",")) {
                    currency = currency.dropLast(1)
                }
                val value = CurrencyEntry(type,currency)
                toReturn.add(CurrencyEntry(type,currency))
            }
        return toReturn
    }


    override fun sendLogsToEmail(){
        val uploadWorkRequest: WorkRequest =
            OneTimeWorkRequestBuilder<SendLogsWorker>()
                .build()
        workManager.enqueue(uploadWorkRequest)
    }
}