package com.ardxclient.absenspn.utils

import android.content.Context
import com.ardxclient.absenspn.model.response.UserLoginResponse
import com.google.gson.Gson

object SessionUtils {
    private const val SESION_KEY = "USER_SESSION"
    fun saveUser(context: Context, user: UserLoginResponse) {
        val userJson = Gson().toJson(user)
        StorageUtils.saveString(context, SESION_KEY, userJson)
    }

    fun getUser(context: Context): UserLoginResponse? {
        val userJson = StorageUtils.getString(context, SESION_KEY)
        return if (userJson != null) {
            Gson().fromJson(userJson, UserLoginResponse::class.java)
        } else {
            null
        }
    }
}