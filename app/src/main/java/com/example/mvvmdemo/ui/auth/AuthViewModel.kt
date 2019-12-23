package com.example.mvvmdemo.ui.auth

import android.view.View
import androidx.lifecycle.ViewModel
import com.example.mvvmdemo.data.repositories.UserRepository
import com.example.mvvmdemo.util.ApiException
import com.example.mvvmdemo.util.Coroutines
import com.example.mvvmdemo.util.NoInternetException

class AuthViewModel (private val repository: UserRepository) : ViewModel() {

    var email: String? = null
    var password: String? = null
    var authListener: AuthListener? = null

    fun getLoggedInUser () = repository.getUser()

    fun onLoginButtonClick(view: View){
        authListener?.onStarted()

        if(password.isNullOrEmpty() || email.isNullOrEmpty()){
            authListener?.onFailure("Invalid Email or Password")
            return
        }

        // success
        Coroutines.main {
            try {
                val authResponse = repository.userLogin(email!!,password!!)
                authResponse.user?.let {
                    authListener?.onSuccess(it)
                    repository.saveUser(it)
                    return@main
                }
                authListener?.onFailure(authResponse.message!!)

            }catch (e : ApiException){
                authListener?.onFailure(e.message!!)
            }catch (e : NoInternetException){
                authListener?.onFailure(e.message!!)
            }
        }
    }
}