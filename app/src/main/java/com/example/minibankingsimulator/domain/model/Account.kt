package com.example.minibankingsimulator.domain.model

import com.example.minibankingsimulator.data.local.entity.AccountEntity
import com.example.minibankingsimulator.domain.enums.CurrencyType
import java.math.BigDecimal

data class Account(
    val id: String,
    val iban: String,
    val balance: BigDecimal,
    val currency: CurrencyType,
    val cards: List<Card>,
)

fun AccountEntity.toDomain(cards: List<Card> = emptyList()): Account {
    return Account(
        id = this.accountId,
        iban = this.iban,
        balance = this.balance,
        currency = this.currency,
        cards = cards
    )
}

fun Account.toEntity(userId: String): AccountEntity {
    return AccountEntity(
        accountId = this.id,
        userId = userId,
        iban = this.iban,
        balance = this.balance,
        currency = this.currency,
    )
}