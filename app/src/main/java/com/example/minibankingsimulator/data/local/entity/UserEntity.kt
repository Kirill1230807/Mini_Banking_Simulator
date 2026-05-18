package com.example.minibankingsimulator.data.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.util.UUID

@Entity(tableName = "users_table", indices = [Index(value = ["email"], unique = true)])
data class UserEntity(
    @PrimaryKey
    val userId: String = UUID.randomUUID().toString(),

    val firstName: String,

    val lastName: String,

    val email: String,

    val password: String,

    val phoneNumber: String,

    val timestamp: Long
)

data class UserWithAccounts(
    @Embedded val user: UserEntity,
    @Relation(
        parentColumn = "userId",
        entityColumn = "userId"
    )
    val accounts: List<AccountEntity>
)