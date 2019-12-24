package com.example.mvvmdemo.ui.auth

import android.content.Intent
import android.view.View
import androidx.lifecycle.ViewModel
import com.example.mvvmdemo.data.repositories.UserRepository
import com.example.mvvmdemo.util.ApiException
import com.example.mvvmdemo.util.Coroutines
import com.example.mvvmdemo.util.NoInternetException

class AuthViewModel (private val repository: UserRepository) : ViewModel() {

    var email: String? = null
    var password: String? = null
    var passwordConfirm: String? = null
    var name: String? = null

    var authListener: AuthListener? = null

    fun getLoggedInUser () = repository.getUser()

    fun onLoginButtonClick(view: View){
        authListener?.onStarted()

        if(password.isNullOrEmpty() || email.isNullOrEmpty()){
            authListener?.onFailure("Invalid Email or Password")
            return
        }

        // success
        // Coroutine scope
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

    fun onSignupButtonClick(view: View){
        authListener?.onStarted()

        if(name.isNullOrEmpty()){
            authListener?.onFailure("Name is required")
            return
        }
        if(email.isNullOrEmpty()){
            authListener?.onFailure("Email is required")
            return
        }
        if(password.isNullOrEmpty()){
            authListener?.onFailure("Password is required")
            return
        }
        if(passwordConfirm.isNullOrEmpty()){
            authListener?.onFailure("Confirm Password is required")
            return
        }
        if(password != passwordConfirm){
            authListener?.onFailure("Password does not match")
            return
        }

        // success
        // Coroutine scope
        Coroutines.main {
            try {
                val authResponse = repository.userSignup(name!!,email!!,password!!)
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

    fun goToSignup (view:View){
        Intent(view.context,SignupActivity::class.java).also {
            view.context.startActivity(it)
        }
    }

    fun goToLogin (view: View){
        Intent(view.context,LoginActivity::class.java).also {
            view.context.startActivity(it)
        }
    }
}