package com.example.minibankingsimulator.domain.model

import com.example.minibankingsimulator.data.local.entity.CardEntity
import com.example.minibankingsimulator.domain.enums.CardType

data class Card(
    val id: String,
    val cardHolder: String,
    val cardNumber: String,
    val expiryDate: String,
    val cvv: String,
    val type: CardType,
    val timestamp: String
)

fun CardEntity.toDomain() : Card {
    return Card(
        id = this.cardId,
        cardHolder = this.cardHolder,
        cardNumber = this.cardNumber,
        expiryDate = this.expiryDate,
        cvv = this.cvv,
        type = this.cardType,
        timestamp = this.timestamp.toString()
    )
}