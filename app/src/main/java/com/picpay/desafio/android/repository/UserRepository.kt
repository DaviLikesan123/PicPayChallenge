package com.picpay.desafio.android.repository

import androidx.lifecycle.LiveData
import com.picpay.desafio.android.api.PicPayService
import com.picpay.desafio.android.model.User

class UserRepository(private val userDao: UserDao, private val apiService: PicPayService) {

    val users: LiveData<List<User>> = userDao.getAllUsers() 

    suspend fun fetchUsersFromApi() {
        try {
            val usersFromApi = apiService.getUsers()
            userDao.insertAll(usersFromApi)
        } catch (e: Exception) {
            throw Exception("Dê uma olhada na sua conexão")
        }
    }
}






