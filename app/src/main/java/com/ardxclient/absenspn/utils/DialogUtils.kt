package com.ardxclient.absenspn.utils

import android.content.Context
import com.google.android.material.dialog.MaterialAlertDialogBuilder

object DialogUtils {
    interface OnDeleteConfirmListener {
        fun onDeleteConfirmed()
    }
    fun showDeleteDialog(context: Context, listener: OnDeleteConfirmListener) {
        MaterialAlertDialogBuilder(context)
            .setTitle("Konfirmasi")
            .setMessage("Apakah anda yakin ingin menghapus data ini?")
            .setNegativeButton("Batalkan") { dialog, _ -> dialog.dismiss()}
            .setPositiveButton("Konfirmasi") { _, _ -> listener.onDeleteConfirmed() }
            .show()
    }
}