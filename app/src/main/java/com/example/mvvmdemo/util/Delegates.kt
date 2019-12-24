package com.example.mvvmdemo.util

import kotlinx.coroutines.*

// Custom lazy block that will use the coroutine scope

// This function contains a block of suspended function in a parameter that will be executed within the coroutine scope
fun <T> lazyDeferred(block : suspend CoroutineScope.() -> T) : Lazy<Deferred<T>>{   //Deferred is a job with a result

    return lazy {
        GlobalScope.async(start = CoroutineStart.LAZY) {
            block.invoke(this)
        }
    }
}