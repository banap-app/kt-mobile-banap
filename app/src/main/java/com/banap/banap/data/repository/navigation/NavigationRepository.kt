package com.banap.banap.data.repository.navigation

import android.content.Context
import androidx.datastore.preferences.core.edit
import com.banap.banap.core.data.datastore.navigation.SettingsDataStore
import com.banap.banap.core.data.datastore.navigation.SettingsDataStore.dataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NavigationRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val dataStore = context.dataStore

    val lastScreen: Flow<String> = dataStore.data
        .map { preferences ->
            preferences[SettingsDataStore.LAST_SCREEN_KEY] ?: "Home"
        }

    suspend fun saveLastScreen(route: String) {
        dataStore.edit { preferences ->
            preferences[SettingsDataStore.LAST_SCREEN_KEY] = route
        }
    }
}