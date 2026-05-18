package com.example.minibankingsimulator.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.minibankingsimulator.data.local.dao.AccountDao
import com.example.minibankingsimulator.data.local.dao.CardDao
import com.example.minibankingsimulator.data.local.dao.UserDao
import com.example.minibankingsimulator.data.local.entity.AccountEntity
import com.example.minibankingsimulator.data.local.entity.CardEntity
import com.example.minibankingsimulator.data.local.entity.UserEntity


@Database(
    entities = [
        UserEntity::class,
        AccountEntity::class,
        CardEntity::class
    ],
    version = 1,
    exportSchema = false
)

@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao() : UserDao
    abstract fun accountDao() : AccountDao
    abstract fun cardDao() : CardDao
}