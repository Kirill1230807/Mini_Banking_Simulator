package com.example.minibankingsimulator.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.minibankingsimulator.data.local.entity.AccountEntity
import com.example.minibankingsimulator.data.local.entity.AccountWithCards
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDao {
    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAccount(
        account: AccountEntity
    )

    @Update
    suspend fun updateAccount(
        account: AccountEntity
    )

    @Delete
    suspend fun deleteAccount(
        account: AccountEntity
    )

    @Query(
        """
        SELECT * FROM accounts_table
        WHERE accountId = :accountId
    """
    )
    suspend fun getAccountById(
        accountId: String
    ): AccountEntity?

    @Query(
        """
        SELECT * FROM accounts_table
        WHERE userId = :userId
    """
    )
    suspend fun getAccountsByUserId(
        userId: String
    ): List<AccountEntity>
    
    @Query(
        """
        UPDATE accounts_table
        SET balance = :newBalance
        WHERE accountId = :accountId
    """
    )
    suspend fun updateBalance(
        accountId: String,
        newBalance: Double
    )

    @Query(
        """
        DELETE FROM accounts_table
        WHERE userId = :userId
    """
    )
    suspend fun deleteAccountsByUserId(userId: String)

    @Transaction
    @Query("SELECT * FROM accounts_table WHERE userId = :userId")
    suspend fun getAccountsWithCardsByUserId(userId: String): List<AccountWithCards>
}