package com.daniel.appetizemenu

import android.content.Context

class SessionManager(private val context: Context) {

    private val sharedPreferences =
        context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        val editor = sharedPreferences.edit()
        editor.putString("TOKEN_KEY", token)
        editor.apply()
    }

    fun clearSession() {
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    fun checkSavedCredentials(): String? {
        return sharedPreferences.getString("TOKEN_KEY", null)
    }
}