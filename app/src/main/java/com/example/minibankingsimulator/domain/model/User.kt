package com.example.minibankingsimulator.domain.model

import com.example.minibankingsimulator.data.local.entity.UserEntity

data class User(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val phoneNumber: String,
    val accounts: List<Account>,
)

fun UserEntity.toDomain(accounts: List<Account> = emptyList()): User {
    return User(
        firstName = this.firstName,
        lastName = this.lastName,
        email = this.email,
        password = this.password,
        phoneNumber = this.phoneNumber,
        accounts = accounts,
    )
}

fun User.toEntity(): UserEntity {
    return UserEntity(
        firstName = this.firstName,
        lastName = this.lastName,
        email = this.email,
        password = this.password,
        phoneNumber = this.phoneNumber,
        timestamp = System.currentTimeMillis()
    )
}
