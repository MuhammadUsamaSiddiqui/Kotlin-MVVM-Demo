package com.example.mvvmdemo.data.network.responses

import com.example.mvvmdemo.data.db.entities.Quote

data class QuotesResponse(
    val isSuccessful : Boolean,
    val quotes : List<Quote>
)