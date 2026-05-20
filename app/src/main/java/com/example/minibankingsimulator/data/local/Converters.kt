package com.example.minibankingsimulator.data.local

import androidx.room.TypeConverter
import com.example.minibankingsimulator.domain.enums.CardType
import com.example.minibankingsimulator.domain.enums.CurrencyType
import java.math.BigDecimal

class Converters {
    // CurrencyType
    @TypeConverter
    fun fromCurrencyType(value: CurrencyType): String {
        return value.name
    }

    @TypeConverter
    fun toCurrencyType(value: String): CurrencyType {
        return CurrencyType.valueOf(value)
    }

    // CardType
    @TypeConverter
    fun fromCardType(value: CardType): String {
        return value.name
    }

    @TypeConverter
    fun toCardType(value: String): CardType {
        return CardType.valueOf(value)
    }

    // For BigDecimal
    @TypeConverter
    fun fromBigDecimal(value: BigDecimal?): String? {
        return value?.toString()
    }

    @TypeConverter
    fun toBigDecimal(value: String?): BigDecimal? {
        return value?.let { BigDecimal(it) }
    }
}