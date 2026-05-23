package com.example.minibankingsimulator.data.repositoryImpl

import com.example.minibankingsimulator.data.local.dao.AccountDao
import com.example.minibankingsimulator.domain.model.Account
import com.example.minibankingsimulator.domain.repository.AccountRepository
import kotlinx.coroutines.flow.Flow

class AccountRepositoryImpl(
    private val accountDao: AccountDao
) : AccountRepository {
    override fun getAccounts(): Flow<List<Account>> {
        TODO("Not yet implemented")
    }

    override suspend fun getAccountById(id: String): Account? {
        TODO("Not yet implemented")
    }

    override suspend fun insertAccount(account: Account) {
        TODO("Not yet implemented")
    }

    override suspend fun updateBalance(accountId: String, amount: Double) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAccount(account: Account) {
        TODO("Not yet implemented")
    }
}