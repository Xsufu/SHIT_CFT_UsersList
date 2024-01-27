package com.example.shitcftuserslist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shitcftuserslist.databinding.FragmentUsersListBinding
import com.example.shitcftuserslist.network.MainAPI
import com.example.shitcftuserslist.room.ResultsApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UsersListFragment : Fragment() {

    // Создание
    private var _binding: FragmentUsersListBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainAPI: MainAPI
    private val viewModel: ResultsViewModel by activityViewModels {
        ResultsViewModel.ResultViewModelFactory(
            (activity?.application as ResultsApplication).database.userDao()
        )
    }
    private lateinit var adapter: UserListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUsersListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = UserListAdapter {
            val action =
                UsersListFragmentDirections.actionUsersListFragmentToUserDetailsFragment(it.id)

            this.findNavController().navigate(action)
        }
        binding.recyclerView.adapter = adapter

        initRetrofit()
        loadData()
        refreshData()
    }

    private fun initRetrofit() {
        // Создаём перехватчик и указываем его уровень
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        // Создаём клиент с нашим перехватчиком
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        // Создаём билдер
        val retrofit = Retrofit.Builder()
            .baseUrl("https://randomuser.me")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        mainAPI = retrofit.create(MainAPI::class.java)
    }

    // Загрузка данных на экран
    private fun loadData() {
        // Получение количества записей в БД
        viewModel.isFilled().observe(this.viewLifecycleOwner) { filledFlag ->
            // Если записей нет
            if (filledFlag == 0) {
                // Получить данные с API
                // TODO: Найти решение с обработкой ошибок через Retrofit
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val response = mainAPI.getUsersData(20)
                        // Добавление данных в БД
                        viewModel.addUsers(response.results)
                    } catch (e: Exception) {
                        requireActivity().runOnUiThread {
                            Toast.makeText(
                                requireContext(),
                                "Ошибка при получении данных. \nПроверьте подключение к сети",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }

        // Вывод пользователей из БД в RecyclerView
        viewModel.getAllUsers.observe(this.viewLifecycleOwner) { users ->
            users.let {
                adapter.submitList(it)
            }
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this.context)
    }

    // Метод обновления данных при скролле сверху вниз
    private fun refreshData() {
        binding.refreshLayout.setOnRefreshListener {
            // Очистка базы данных
            viewModel.clearDatabase()
            //Повторная загрузка
            loadData()

            // Остановка анимации при завершении загрузки
            binding.refreshLayout.isRefreshing = false
        }
    }
}