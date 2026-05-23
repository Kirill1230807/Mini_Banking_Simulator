package com.example.minibankingsimulator.domain.repository

import com.example.minibankingsimulator.domain.model.Account
import kotlinx.coroutines.flow.Flow


interface AccountRepository {
    fun getAccounts(): Flow<List<Account>>

    suspend fun getAccountById(id: String): Account?

    suspend fun insertAccount(account: Account)

    suspend fun updateBalance(accountId: String, amount: Double)

    suspend fun deleteAccount(account: Account)
}