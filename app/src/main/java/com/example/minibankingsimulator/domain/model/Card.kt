package com.example.minibankingsimulator.domain.model

import com.example.minibankingsimulator.data.local.entity.CardEntity
import com.example.minibankingsimulator.domain.enums.CardType

data class Card(
    val cardHolder: String,
    val cardNumber: String,
    val expiryDate: String,
    val cvv: String,
    val type: CardType,
)

fun CardEntity.toDomain() : Card {
    return Card(
        cardHolder = this.cardHolder,
        cardNumber = this.cardNumber,
        expiryDate = this.expiryDate,
        cvv = this.cvv,
        type = this.cardType,
    )
}