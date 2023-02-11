package net.herasevyan.tictactoe.ui.game_history

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult

class DeleteGameHistoryDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setMessage("Delete history?")
            .setPositiveButton("yes") { _, _ ->
                setFragmentResult("deleteKey", bundleOf())
                dismiss()
            }.setNegativeButton("no") { _, _ ->
                dismiss()
            }.create()
}