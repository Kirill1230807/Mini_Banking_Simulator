package com.example.minibankingsimulator.data.repositoryImpl

import com.example.minibankingsimulator.data.local.dao.AccountDao
import com.example.minibankingsimulator.data.local.dao.CardDao
import com.example.minibankingsimulator.data.local.dao.UserDao
import com.example.minibankingsimulator.data.local.datastore.SessionManager
import com.example.minibankingsimulator.data.local.entity.AccountEntity
import com.example.minibankingsimulator.data.local.entity.CardEntity
import com.example.minibankingsimulator.data.local.entity.UserEntity
import com.example.minibankingsimulator.domain.enums.CardType
import com.example.minibankingsimulator.domain.enums.CurrencyType
import com.example.minibankingsimulator.domain.model.User
import com.example.minibankingsimulator.domain.model.toDomain
import com.example.minibankingsimulator.domain.repository.AuthRepository
import kotlinx.coroutines.flow.firstOrNull
import java.math.BigDecimal
import java.util.UUID
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
    private val accountDao: AccountDao,
    private val cardDao: CardDao,
    private val sessionManager: SessionManager,
) : AuthRepository {
    override suspend fun register(
        firstName: String,
        lastName: String,
        phoneNumber: String,
        password: String,
        email: String
    ): Result<User> {
        return try {


            val existingUser = userDao.getUserByEmail(email)

            if (existingUser != null) {
                return Result.failure(
                    Exception("User already exists")
                )
            }

            val userId = UUID.randomUUID().toString()

            val hashedPassword = hashPassword(password)

            val userEntity = UserEntity(
                userId = userId,
                firstName = firstName,
                lastName = lastName,
                email = email,
                password = hashedPassword,
                phoneNumber = phoneNumber,
                timestamp = System.currentTimeMillis()
            )

            userDao.insertUser(userEntity)

            val accountId = UUID.randomUUID().toString()

            val accountEntity = AccountEntity(
                accountId = accountId,
                userId = userId,
                iban = generateIban(),
                balance = BigDecimal.ZERO,
                currency = CurrencyType.UAH
            )

            accountDao.insertAccount(accountEntity)

            val cardEntity = CardEntity(
                cardId = UUID.randomUUID().toString(),
                accountId = accountId,
                cardNumber = generateCardNumber(),
                expiryDate = generateExpiryDate(),
                cardType = CardType.MASTERCARD,
                cvv = generateCvv(),
                cardHolder = "$firstName $lastName".uppercase(),
                timestamp = System.currentTimeMillis(),
            )

            cardDao.insertCard(cardEntity)

            sessionManager.saveSession(userId)

            val cardDomain = cardEntity.toDomain()

            val accountDomain = accountEntity.toDomain(cards = listOf(cardDomain))

            val userDomain = userEntity.toDomain(accounts = listOf(accountDomain))

            Result.success(userDomain)

        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    override suspend fun login(
        email: String,
        password: String
    ): Result<User> {
        return try {
            val userEntity = userDao.getUserByEmail(email)
                ?: return Result.failure(Exception("User not found"))

            val enteredPassword = hashPassword(password)

            if (userEntity.password != enteredPassword) {
                return Result.failure(Exception("Enter another password"))
            }

            val accountsWithCards = accountDao.getAccountsWithCardsByUserId(userEntity.userId)

            val accountsDomain = accountsWithCards.map { accountWithCards ->
                val cardsDomain = accountWithCards.cards.map { it.toDomain() }
                accountWithCards.account.toDomain(cards = cardsDomain)
            }

            sessionManager.saveSession(userEntity.userId)

            Result.success(userEntity.toDomain(accounts = accountsDomain))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun logout() {
        sessionManager.clearSession()
    }

    override suspend fun getCurrentUser(): User? {
        val userId = sessionManager.userId.firstOrNull() ?: return null

        val userEntity = userDao.getUserById(userId) ?: return null

        val accountsWithCards = accountDao.getAccountsWithCardsByUserId(userId)

        val accountsDomain = accountsWithCards.map { accountWithCards ->
            val cardsDomain = accountWithCards.cards.map { it.toDomain() }
            accountWithCards.account.toDomain(cards = cardsDomain)
        }

        return userEntity.toDomain(accounts = accountsDomain)
    }

    private fun hashPassword(password: String): String {
        return password.hashCode().toString()
    }

    private fun generateIban(): String {

        return "UA" + (100000000..999999999).random()
    }

    private fun generateCardNumber(): String {

        return List(16) {
            (0..9).random()
        }.joinToString("")
    }

    private fun generateExpiryDate(): String {

        return "12/29"
    }

    private fun generateCvv(): String {

        return (100..999).random().toString()
    }
}