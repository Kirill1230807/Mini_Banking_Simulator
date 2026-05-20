package com.example.minibankingsimulator.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.minibankingsimulator.data.local.entity.CardEntity

@Dao
interface CardDao {

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCard(
        card: CardEntity
    )

    @Update
    suspend fun updateCard(
        card: CardEntity
    )

    @Delete
    suspend fun deleteCard(
        card: CardEntity
    )

    @Query("""
        SELECT * FROM cards_table
        WHERE cardId = :cardId
    """)
    suspend fun getCardById(
        cardId: String
    ): CardEntity?

    @Query("""
        SELECT * FROM cards_table
        WHERE accountId = :accountId
    """)
    suspend fun getCardsByAccountId(
        accountId: String
    ): List<CardEntity>

    @Query("""
        SELECT * FROM cards_table
        WHERE cardNumber = :cardNumber
    """)
    suspend fun getCardByNumber(
        cardNumber: String
    ): CardEntity?

    @Query("SELECT * FROM cards_table")
    suspend fun getAllCards(): List<CardEntity>

    @Query("""
        DELETE FROM cards_table
        WHERE accountId = :accountId
    """)
    suspend fun deleteCardsByAccountId(
        accountId: String
    )
}