package com.example.mvvmdemo

import android.app.Application
import com.example.mvvmdemo.data.db.database.AppDatabase
import com.example.mvvmdemo.data.network.api.MyApi
import com.example.mvvmdemo.data.network.interceptor.NetworkConnectionInterceptor
import com.example.mvvmdemo.data.repositories.QuotesRepository
import com.example.mvvmdemo.data.repositories.UserRepository
import com.example.mvvmdemo.ui.auth.AuthViewModelFactory
import com.example.mvvmdemo.ui.home.profile.ProfileViewModelFactory
import com.example.mvvmdemo.ui.home.quotes.QuotesViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

// For dependency injection

class MVVMApplication : Application(), KodeinAware {   //Base class for our application and it is instantiated before anything else

    override val kodein: Kodein = Kodein.lazy {

        import(androidXModule(this@MVVMApplication))

        bind() from  singleton { NetworkConnectionInterceptor(instance()) }
        bind() from  singleton { MyApi(instance()) }
        bind() from  singleton { AppDatabase(instance()) }
        bind() from  singleton {UserRepository(instance(), instance())}
        bind() from  singleton {QuotesRepository(instance(), instance())}
        bind() from provider {AuthViewModelFactory(instance())}
        bind() from provider {ProfileViewModelFactory(instance())}
        bind() from provider {QuotesViewModelFactory(instance())}
    }
}