package com.picpay.desafio.android

import android.content.Context
import android.content.ContextWrapper
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.picpay.desafio.android.api.PicPayService
import com.picpay.desafio.android.model.User
import com.picpay.desafio.android.repository.UserDao
import com.picpay.desafio.android.repository.UserDatabase
import com.picpay.desafio.android.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock

@RunWith(AndroidJUnit4::class)
class TestDatabase(base: Context) : ContextWrapper(base) {

    private lateinit var database: UserDatabase
    private lateinit var userDao: UserDao
    private lateinit var userRepository: UserRepository

    @Before
    fun createDb() {
       val database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            UserDatabase::class.java
        ).build()
        userDao = database.userDao()

        val mockPicPayService = mock(PicPayService::class.java)

        userRepository = UserRepository(userDao, mockPicPayService)
    }

    @Test
    fun testDatabase() {

        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            val userTest = User("", "", 70, "")
            userDao.insertUser(user = userTest)

            withContext(Dispatchers.IO) {
                Thread.sleep(3000)
            }

            userDao.deleteUserById(70)
        }
    }
    @After
    fun closeDb() {
        database.close()
    }
}