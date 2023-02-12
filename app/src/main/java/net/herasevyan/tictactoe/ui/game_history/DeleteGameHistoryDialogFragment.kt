package net.herasevyan.tictactoe.ui.game_history

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import net.herasevyan.tictactoe.R

class DeleteGameHistoryDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext(), R.style.AlertDialogTheme)
            .setMessage(R.string.dialog_message_delete_history)
            .setPositiveButton(R.string.yes) { _, _ ->
                setFragmentResult("deleteKey", bundleOf())
                dismiss()
            }.setNegativeButton(R.string.no) { _, _ ->
                dismiss()
            }.create()
}