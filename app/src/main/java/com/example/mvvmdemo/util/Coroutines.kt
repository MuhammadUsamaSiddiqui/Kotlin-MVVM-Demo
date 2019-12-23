package com.example.mvvmdemo.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object Coroutines {  //here object is equivalent to static in java

    fun main (work : suspend(() -> Unit)) =    // This function return a job
        CoroutineScope(Dispatchers.Main).launch {
            work()
        }
}