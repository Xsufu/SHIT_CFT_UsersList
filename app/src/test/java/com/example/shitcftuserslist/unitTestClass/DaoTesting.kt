package com.example.shitcftuserslist.unitTestClass

import android.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.shitcftuserslist.data.Coordinates
import com.example.shitcftuserslist.data.Dob
import com.example.shitcftuserslist.data.Location
import com.example.shitcftuserslist.data.Name
import com.example.shitcftuserslist.data.Picture
import com.example.shitcftuserslist.data.Registered
import com.example.shitcftuserslist.data.Result
import com.example.shitcftuserslist.data.Street
import com.example.shitcftuserslist.model.ResultsViewModel
import com.example.shitcftuserslist.room.User
import com.example.shitcftuserslist.room.UserDao
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExtendWith(MockitoExtension::class)
class NoWayTest() {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var userDao: UserDao

    @Mock
    private lateinit var userObserver: Observer<List<User>>

    private lateinit var viewModel: ResultsViewModel

    @BeforeEach
    fun setUp() {
        viewModel = ResultsViewModel(userDao)
        viewModel.getAllUsers.observeForever(userObserver)
    }

    @Test
    fun getUser() = runBlocking {
        val user = User(
            id = 1,
            name = "Mr Josh Dun",
            street = "Mulberry st, 21",
            city = "Dema",
            state = "Ohio",
            country = "USA",
            latitude = "32.123",
            longitude = "-63.132",
            email = "test@test.com",
            dob = "2000",
            phone = "+1 753 232",
            cell = "555 123 123",
            image = "test.link/1"
        )

        whenever(userDao.getUsers()).thenReturn(flowOf(listOf(user)))

        viewModel.addUsers(
            listOf(
                Result(
                    cell = "555 123 123",
                    dob = Dob(24, "2000"),
                    email = "test@test.com",
                    gender = "F",
                    location = Location(
                        city = "Dema",
                        state = "Ohio",
                        country = "USA",
                        postcode = "123123",
                        street = Street("Mulberry st", 21),
                        coordinates = Coordinates("32.123", "-63.132")
                    ),
                    name = Name("Josh", "Dun", "Mr"),
                    phone = "+1 753 232",
                    picture = Picture("test.link/1", "test.link/2", "test.link/3"),
                    registered = Registered(12, "2012")
                )
            )
        )
        verify(userObserver).onChanged(listOf(user))
    }
}