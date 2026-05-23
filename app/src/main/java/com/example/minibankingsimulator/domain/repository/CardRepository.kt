package com.example.minibankingsimulator.domain.repository

import com.example.minibankingsimulator.domain.model.Card
import kotlinx.coroutines.flow.Flow


interface CardRepository {
    fun getCards(): Flow<List<Card>>
    suspend fun getCardById(id: String): Card?
    suspend fun insertCard(card: Card)
    suspend fun deleteCard(card: Card)
}