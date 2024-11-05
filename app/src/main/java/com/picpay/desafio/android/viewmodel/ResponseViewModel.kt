package com.picpay.desafio.android.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.picpay.desafio.android.api.ApiService
import com.picpay.desafio.android.api.PicPayService
import com.picpay.desafio.android.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResponseViewModel : ViewModel() {

    private val apiService = ApiService()
    private val service: PicPayService = apiService.createService()

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> get() = _users

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun fetchUsers() {
        _loading.value = true
        service.getUsers().enqueue(object : Callback<List<User>> {
            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                _loading.value = false
                _error.value = "Error: ${t.message}"
            }

            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                _loading.value = false
                if (response.isSuccessful) {
                    _users.value = response.body()
                } else {
                    _error.value = "Error: ${response.message()}"
                }
            }
        })
    }

}