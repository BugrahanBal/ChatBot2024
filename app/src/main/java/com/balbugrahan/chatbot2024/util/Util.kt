package com.balbugrahan.chatbot2024.util

import android.app.AlertDialog
import android.content.Context

object DialogHelper {
    fun showAlertDialog(
        context: Context,
        title: String,
        message: String,
        positiveText: String,
        negativeText: String,
        positiveAction: (() -> Unit)? = null,
        negativeAction: (() -> Unit)? = null
    ) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton(positiveText) { dialog, _ ->
            positiveAction?.invoke()
            dialog.dismiss()
        }
        builder.setNegativeButton(negativeText) { dialog, _ ->
            negativeAction?.invoke()
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }
}