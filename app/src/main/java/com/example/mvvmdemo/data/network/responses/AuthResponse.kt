package com.example.mvvmdemo.data.network.responses

import com.example.mvvmdemo.data.db.entities.User

data class AuthResponse (
    val isSuccessful : Boolean?,
    val message : String?,
    val  user : User?
)