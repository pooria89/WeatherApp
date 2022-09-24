package com.app.data.db

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.huxq17.download.DownloadProvider.context
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataStoreManager @Inject constructor(
) {

    companion object {
        var LATITUDE = stringPreferencesKey("LATITUDE")
        var LONGITUDE = stringPreferencesKey("LONGITUDE")
    }

    private val Context.dataStoreManager: DataStore<Preferences> by preferencesDataStore(
        name = "cookie"
    )

    suspend fun saveLatitude(latitude: String) {
        context.dataStoreManager.edit {
            it[LATITUDE] = latitude
        }
    }

    fun getLatitude() = context.dataStoreManager.data.map {
        it[LATITUDE]
    }

    suspend fun saveLongitude(longitude: String) {
        context.dataStoreManager.edit {
            it[LONGITUDE] = longitude
        }
    }

    fun getLongitue() = context.dataStoreManager.data.map {
        it[LONGITUDE]
    }
}