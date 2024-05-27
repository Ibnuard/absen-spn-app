package com.ardxclient.absenspn.utils

import com.google.android.material.textfield.TextInputLayout

object InputUtils {
    fun isAllFieldComplete(vararg fields: TextInputLayout): Boolean{
        for (field in fields) {
            if (field.editText?.text!!.isEmpty()) {
                field.error = "field tidak boleh kosong."
                return false
            }else{
                field.isErrorEnabled = false
                field.error = null
            }
        }
        return true
    }

    fun isValidUsername(field: TextInputLayout): Boolean {
        // Regex pattern to match only alphanumeric characters (no spaces or symbols)
        val value = field.editText?.text

        return if (value != null){
            val usernamePattern = "^[a-zA-Z0-9]+$".toRegex()
            val isValid = value.matches(usernamePattern)
            if (isValid){
                true
            }else{
                field.error = "Username tidak boleh mengandung spasi atau simbol."
                false
            }
        }else{
            false
        }
    }
}