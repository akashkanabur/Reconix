package com.reconix.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "reconix_prefs")

@Singleton
class PreferencesManager @Inject constructor(@ApplicationContext private val context: Context) {

    private object Keys {
        val IS_ONBOARDED = booleanPreferencesKey("is_onboarded")
        val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
        val USER_EMAIL = stringPreferencesKey("user_email")
        val BIOMETRIC_ENABLED = booleanPreferencesKey("biometric_enabled")
        val API_KEY = stringPreferencesKey("api_key")
    }

    val isOnboarded: Flow<Boolean> = context.dataStore.data.map { it[Keys.IS_ONBOARDED] ?: false }
    val isLoggedIn: Flow<Boolean> = context.dataStore.data.map { it[Keys.IS_LOGGED_IN] ?: false }
    val userEmail: Flow<String> = context.dataStore.data.map { it[Keys.USER_EMAIL] ?: "" }
    val biometricEnabled: Flow<Boolean> = context.dataStore.data.map { it[Keys.BIOMETRIC_ENABLED] ?: true }

    suspend fun setOnboarded(value: Boolean) = context.dataStore.edit { it[Keys.IS_ONBOARDED] = value }
    suspend fun setLoggedIn(value: Boolean) = context.dataStore.edit { it[Keys.IS_LOGGED_IN] = value }
    suspend fun setUserEmail(email: String) = context.dataStore.edit { it[Keys.USER_EMAIL] = email }
    suspend fun setBiometricEnabled(value: Boolean) = context.dataStore.edit { it[Keys.BIOMETRIC_ENABLED] = value }
    suspend fun setApiKey(key: String) = context.dataStore.edit { it[Keys.API_KEY] = key }
    suspend fun clearAll() = context.dataStore.edit { it.clear() }
}
