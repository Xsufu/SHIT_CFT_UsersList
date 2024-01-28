package com.example.shitcftuserslist.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    /**
     * Получение всех пользователей из БД
     */
    @Query("SELECT * FROM user")
    fun getUsers(): Flow<List<User>>

    /**
     * Запрос на очистку БД
     */
    @Query("DELETE FROM user")
    suspend fun clearDataBase()

    /**
     * Проверка на наличие данных в БД
     */
    @Query("SELECT COUNT(id) FROM user")
    fun isFilled(): Flow<Int>

    /**
     * Получение пользователя по ID
     *
     * @param id идентификатор искомого пользователя
     */
    @Query("SELECT * FROM user WHERE id = :id")
    fun getUser(id: Int): Flow<User>

    /**
     * Добавление пользователя в БД
     *
     * @param user пользователь, которого необходимо добавить в БД
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: User)
}