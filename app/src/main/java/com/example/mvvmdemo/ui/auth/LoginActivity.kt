package com.example.mvvmdemo.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.mvvmdemo.R
import com.example.mvvmdemo.data.db.AppDatabase
import com.example.mvvmdemo.data.db.entities.User
import com.example.mvvmdemo.data.network.MyApi
import com.example.mvvmdemo.data.network.NetworkConnectionInterceptor
import com.example.mvvmdemo.data.repositories.UserRepository
import com.example.mvvmdemo.databinding.ActivityLoginBinding
import com.example.mvvmdemo.ui.home.HomeActivity
import com.example.mvvmdemo.util.hide
import com.example.mvvmdemo.util.show
import com.example.mvvmdemo.util.snackbar
import com.example.mvvmdemo.util.toast
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), AuthListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val networkConnectionInterceptor = NetworkConnectionInterceptor(this)
        val api = MyApi(networkConnectionInterceptor)
        val db = AppDatabase(this)
        val repository =  UserRepository(api , db)
        val factory = AuthViewModelFactory(repository)

        val binding: ActivityLoginBinding = DataBindingUtil.setContentView(this,R.layout.activity_login)
        val viewModel = ViewModelProviders.of(this, factory).get(AuthViewModel::class.java)
        binding.viewmodel = viewModel

        viewModel.authListener = this

        viewModel.getLoggedInUser().observe(this, Observer {user->
            if(user !=null){
                Intent(this,HomeActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                }
            }

        })
    }

    override fun onStarted() {
        progressbar.show()
    }

    override fun onSuccess(user: User) {
     /* loginResponse.observe(this, Observer {
          progressbar.hide()
          toast(it)
      })*/
        progressbar.hide()
    //    root_layout.snackbar("${user.name} is Logged in")
    }

    override fun onFailure(message : String) {
        progressbar.hide()
        root_layout.snackbar(message)
    }

}
