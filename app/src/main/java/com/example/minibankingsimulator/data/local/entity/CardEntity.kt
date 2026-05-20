package com.example.minibankingsimulator.data.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.example.minibankingsimulator.domain.enums.CardType
import java.util.UUID

@Entity(
    tableName = "cards_table",
    foreignKeys = [
        ForeignKey(
            entity = AccountEntity::class,
            parentColumns = ["accountId"],
            childColumns = ["accountId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.RESTRICT
        )
    ]
)
data class CardEntity(
    @PrimaryKey
    val cardId: String = UUID.randomUUID().toString(),

    val accountId: String,

    val cardHolder: String,

    val cardNumber: String,

    val expiryDate: String,

    val cvv: String,

    val cardType: CardType,

    val timestamp: Long
)

data class AccountWithCards(
    @Embedded val account: AccountEntity,
    @Relation(
        parentColumn = "accountId",
        entityColumn = "accountId"
    )
    val cards: List<CardEntity>
)