package com.mad.finale

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import java.util.jar.Attributes.Name

object PreferencesKeys {
    val ONBOARDED = booleanPreferencesKey("onboarded")
    val NAME = stringPreferencesKey("name")
    val EMAIL = stringPreferencesKey("email")
    val BIRTHDATE = stringPreferencesKey("birthdate")
}