package com.example.minibankingsimulator.data.repositoryImpl

import com.example.minibankingsimulator.data.local.dao.CardDao
import com.example.minibankingsimulator.domain.model.Card
import com.example.minibankingsimulator.domain.repository.CardRepository
import kotlinx.coroutines.flow.Flow

class CardRepositoryImpl(
    private val cardDao: CardDao
) : CardRepository {
    override fun getCards(): Flow<List<Card>> {
        TODO("Not yet implemented")
    }

    override suspend fun getCardById(id: String): Card? {
        TODO("Not yet implemented")
    }

    override suspend fun insertCard(card: Card) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteCard(card: Card) {
        TODO("Not yet implemented")
    }
}