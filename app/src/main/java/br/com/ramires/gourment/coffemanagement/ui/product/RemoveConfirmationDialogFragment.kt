package br.com.ramires.gourment.coffemanagement.ui.product

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import br.com.ramires.gourment.coffemanagement.R

class RemoveConfirmationDialogFragment(
    private val onConfirm: (Boolean) -> Unit
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            AlertDialog.Builder(it)
                .setTitle(R.string.remove_product_title) // Atualize os IDs de string conforme seu projeto
                .setMessage(R.string.remove_product_message)
                .setPositiveButton(R.string.confirm) { _, _ ->
                    onConfirm(true)
                }
                .setNegativeButton(R.string.cancel) { _, _ ->
                    onConfirm(false)
                }
                .create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}

