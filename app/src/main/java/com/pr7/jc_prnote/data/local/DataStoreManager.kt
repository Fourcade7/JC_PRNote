package com.pr7.jc_prnote.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")


const val SENDED="Sended"

class DataStoreManager constructor(val context: Context) {

    suspend fun saveString(key:String,value:String) {
        val datastorekey= stringPreferencesKey(key)
        context.dataStore.edit { settings ->
            settings[datastorekey]=value
        }
    }

    fun loadString(key:String): Flow<String?> {
        val datastorekey= stringPreferencesKey(key)
        val flow: Flow<String?> =context.dataStore.data
            .map { preferences ->
                // No type safety.
                preferences[datastorekey]
            }

        return flow
    }


    suspend fun saveInt(key:String,value:Int) {
        val datastorekey= intPreferencesKey(key)
        context.dataStore.edit { settings ->
            settings[datastorekey]=value
        }
    }
    fun loadInt(key:String): Flow<Int?> {
        val datastorekey= intPreferencesKey(key)
        val flow: Flow<Int?> =context.dataStore.data
            .map { preferences ->
                // No type safety.
                preferences[datastorekey]
            }

        return flow
    }


    suspend fun saveBoolean(key:String,value:Boolean) {
        val datastorekey= booleanPreferencesKey(key)
        context.dataStore.edit { settings ->
            settings[datastorekey]=value
        }
    }
   fun loadBoolean(key:String): Flow<Boolean?> {
        val datastorekey= booleanPreferencesKey(key)
        val flow: Flow<Boolean?> =context.dataStore.data
            .map { preferences ->
                // No type safety.
                preferences[datastorekey]
            }

        return flow
    }
}