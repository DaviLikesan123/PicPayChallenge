package com.picpay.desafio.android.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.picpay.desafio.android.api.ApiService
import com.picpay.desafio.android.model.User
import com.picpay.desafio.android.repository.UserDatabase
import com.picpay.desafio.android.repository.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val userRepository: UserRepository
    val users: LiveData<List<User>>

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    init {
        val userDao = UserDatabase.getDatabase(application).userDao()
        val apiService = ApiService().createService()
        userRepository = UserRepository(userDao, apiService)
        users = userRepository.users
    }

    fun fetchUsersFromApi() {
        _loading.value = true
        viewModelScope.launch {
            try {
                userRepository.fetchUsersFromApi()
            } catch (e: Exception) {
                _error.value = "Error to find: ${e.message}"
            }
        }
    }
}







