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
}