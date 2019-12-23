package com.example.mvvmdemo.ui.auth

import com.example.mvvmdemo.data.db.entities.User

interface AuthListener {

    fun onStarted()
    fun onSuccess(user: User)
    fun onFailure(message : String)
}