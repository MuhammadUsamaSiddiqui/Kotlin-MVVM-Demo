package com.example.mvvmdemo.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mvvmdemo.data.db.database.AppDatabase
import com.example.mvvmdemo.data.db.entities.Quote
import com.example.mvvmdemo.data.network.SafeApiRequest
import com.example.mvvmdemo.data.network.api.MyApi
import com.example.mvvmdemo.data.preferences.PreferenceProvider
import com.example.mvvmdemo.util.Coroutines
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

private val MINIMUM_INTERVAL = 6

class QuotesRepository (private val api : MyApi, private val db : AppDatabase,
                        private val preferences : PreferenceProvider): SafeApiRequest() {


    private val quotes = MutableLiveData<List<Quote>>()

    init {
        quotes.observeForever {
            // if quotes changed then it stores the updated quotes into database
            saveQuotes(it)
        }
    }

    suspend fun getQuotes(): LiveData<List<Quote>>{
        return withContext(Dispatchers.IO){
            fetchQuotes()
            db.getQuoteDao().getQuotes()
        }
    }

    private suspend fun fetchQuotes(){

        val lastSavedAt = preferences.getLastSavedAt()

        /*if(lastSavedAt ==  null || isFetchNeeded(LocalDateTime.parse(lastSavedAt))){
            val response = apiRequest { api.getQuotes() }
            quotes.postValue(response.quotes)
        }*/

        if(isFetchNeeded()){
            val response = apiRequest { api.getQuotes() }
            quotes.postValue(response.quotes)
        }
    }

    private fun isFetchNeeded(): Boolean {
        return true //ChronoUnit.HOURS.between(lastSavedAt,LocalDateTime.now()) > MINIMUM_INTERVAL
    }

    private fun saveQuotes(quotes: List<Quote>) {
        Coroutines.io {
            preferences.savelastsavedAt(LocalDate.now().toString().trim())
            db.getQuoteDao().saveAllQuotes(quotes)
        }
    }
}