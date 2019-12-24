package com.example.mvvmdemo.ui.home.quotes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mvvmdemo.data.repositories.QuotesRepository
import com.example.mvvmdemo.data.repositories.UserRepository

// To send parameters in constructor of QuotesViewModel
@Suppress("UNCHECKED_CAST")
class QuotesViewModelFactory (private val repository: QuotesRepository) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return QuotesViewModel (repository) as T
    }
}