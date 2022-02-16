package com.soyeb.zerohoursmedicalservice.data_model

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

class UserDetails(private val context: Context) {


    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("pref")

    companion object {
        val USERNAME = stringPreferencesKey("USER_NAME")
        val AGE = stringPreferencesKey("AGE")
    }


    suspend fun storeUser(name: String, age: String) {
        context.dataStore.edit {
            it[USERNAME] = name
            it[AGE] = age
        }
    }


    fun getUserName() = context.dataStore.data.map {
        it[USERNAME] ?: ""
    }

    fun getUserAge() = context.dataStore.data.map {
        it[AGE] ?: ""
    }

}