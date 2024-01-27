package com.example.shitcftuserslist

import android.net.Uri
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.shitcftuserslist.data.Result
import com.example.shitcftuserslist.data.UserResult
import com.example.shitcftuserslist.room.User
import com.example.shitcftuserslist.room.UserDao
import kotlinx.coroutines.launch

class ResultsViewModel(private val userDao: UserDao): ViewModel() {
    val getAllUsers: LiveData<List<User>> = userDao.getUsers().asLiveData()

    fun clearDatabase() {
        viewModelScope.launch {
            userDao.clearDataBase()
        }
    }

    fun isFilled(): LiveData<Int> {
        return userDao.isFilled().asLiveData()
    }

    fun getUserById(id: Int): LiveData<User> {
        return userDao.getUser(id).asLiveData()
    }

    private fun insertUser(user: User) {
        viewModelScope.launch {
            userDao.insertUser(user)
        }
    }

    private fun getNewUserEntry(
        name: String,
        street: String,
        city: String,
        state: String,
        country: String,
        latitude: String,
        longitude: String,
        email: String,
        dob: String,
        phone: String,
        cell: String,
        image: String
    ): User {
        return User(
            name = name,
            street = street,
            city = city,
            state = state,
            country = country,
            latitude = latitude,
            longitude = longitude,
            email = email,
            dob = dob,
            phone = phone,
            cell = cell,
            image = image
        )
    }

    fun addUsers(list: List<Result>) {
        for (user in list) {
            val newUser = getNewUserEntry(
                user.name.title + user.name.first + user.name.last,
                (user.location.street.number).toString() + " " + user.location.street.name,
                user.location.city,
                user.location.state,
                user.location.country,
                user.location.coordinates.latitude,
                user.location.coordinates.longitude,
                user.email,
                user.dob.date,
                user.phone,
                user.cell,
                user.picture.large
            )
            insertUser(newUser)
        }
    }

    class ResultViewModelFactory(private val userDao: UserDao): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            /*
             * Проверяем modelClass, если совпадает с классом InventoryViewModel,
             * возвращаем его экземпляр. Иначе создаём исключение
             */
            if (modelClass.isAssignableFrom(ResultsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ResultsViewModel(userDao) as T
            }
            throw IllegalAccessException("Unknown ViewModel class")
        }
    }
}