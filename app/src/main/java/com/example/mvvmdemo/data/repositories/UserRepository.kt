package com.example.mvvmdemo.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mvvmdemo.data.db.AppDatabase
import com.example.mvvmdemo.data.db.entities.User
import com.example.mvvmdemo.data.network.MyApi
import com.example.mvvmdemo.data.network.SafeApiRequest
import com.example.mvvmdemo.data.network.responses.AuthResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository (private val api : MyApi, private val db : AppDatabase) : SafeApiRequest() {

   /* fun userLogin (email:String , password:String): LiveData<String>{

        val loginResponse = MutableLiveData<String>()

        MyApi().userLogin(email,password)
            .enqueue(object : Callback<ResponseBody> {

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    loginResponse.value = t.message
                }

                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful) {
                        loginResponse.value = response.body()?.string()
                    } else {
                        loginResponse.value = response.errorBody()?.string()
                    }
                }
            })
                    return loginResponse
    }*/

    suspend fun userLogin (email:String , password:String): AuthResponse {
        return apiRequest {
           api.userLogin(email, password)
        }
    }

    suspend fun saveUser(user : User) = db.getUserDao().upsert(user)

    fun getUser() = db.getUserDao().getUser()
}