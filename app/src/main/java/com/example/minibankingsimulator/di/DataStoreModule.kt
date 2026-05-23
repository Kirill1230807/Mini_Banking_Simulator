package com.example.minibankingsimulator.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.minibankingsimulator.data.local.datastore.SessionManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private val Context.session_pref: DataStore<Preferences> by preferencesDataStore(name = "session_prefs")

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.session_pref
    }

    @Provides
    @Singleton
    fun provideSessionManager(dataStore: DataStore<Preferences>): SessionManager {
        return SessionManager(dataStore)
    }
}