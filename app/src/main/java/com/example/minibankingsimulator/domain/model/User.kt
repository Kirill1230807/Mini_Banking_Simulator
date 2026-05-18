package com.example.minibankingsimulator.domain.model

import com.example.minibankingsimulator.data.local.entity.UserEntity

data class User(
    val id: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val phoneNumber: String,
    val accounts: List<Account>,
    val timestamp: String,
)

fun UserEntity.toDomain(accounts: List<Account> = emptyList()): User {
    return User(
        id = this.userId,
        firstName = this.firstName,
        lastName = this.lastName,
        email = this.email,
        password = this.password,
        phoneNumber = this.phoneNumber,
        accounts = accounts,
        timestamp = this.timestamp.toString()
    )
}

fun User.toEntity(): UserEntity {
    return UserEntity(
        userId = this.id,
        firstName = this.firstName,
        lastName = this.lastName,
        email = this.email,
        password = this.password,
        phoneNumber = this.phoneNumber,
        timestamp = System.currentTimeMillis()
    )
}
