package com.example.minibankingsimulator.data.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.example.minibankingsimulator.domain.enums.CurrencyType
import java.math.BigDecimal
import java.util.UUID

@Entity(
    tableName = "accounts_table",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["userId"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.RESTRICT
        )
    ]
    )
data class AccountEntity(
    @PrimaryKey
    val accountId: String = UUID.randomUUID().toString(),

    val userId: String,

    val iban: String,

    val balance: BigDecimal,

    val currency: CurrencyType
)