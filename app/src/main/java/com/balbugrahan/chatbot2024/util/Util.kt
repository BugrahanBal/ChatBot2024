package com.balbugrahan.chatbot2024.util

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import com.balbugrahan.chatbot2024.databinding.CustomAlertDialogBinding

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
        val binding = CustomAlertDialogBinding.inflate(LayoutInflater.from(context))
        val dialog = Dialog(context)

        binding.dialogTitle.text = title
        binding.dialogMessage.text = message
        binding.positiveButton.text = positiveText
        binding.negativeButton.text = negativeText

        binding.positiveButton.setOnClickListener {
            positiveAction?.invoke()
            dialog.dismiss()
        }
        binding.negativeButton.setOnClickListener {
            negativeAction?.invoke()
            dialog.dismiss()
        }

        dialog.setContentView(binding.root)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()
    }
}
