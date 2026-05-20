package com.example.minibankingsimulator.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.minibankingsimulator.data.local.entity.UserEntity
import com.example.minibankingsimulator.data.local.entity.UserWithAccounts

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertUser(user: UserEntity)

    @Query("SELECT * FROM users_table WHERE email = :email")
    suspend fun getUserByEmail(email: String): UserEntity?

    @Transaction
    @Query("SELECT * FROM users_table WHERE userId = :userId")
    suspend fun getUserWithAccounts(userId: String): UserWithAccounts?

    @Query("SELECT * FROM users_table WHERE userId = :userId")
    suspend fun getUserById(userId: String): UserEntity?
}
