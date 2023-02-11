package net.herasevyan.tictactoe.ui.game_history

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult

class DeleteGameFieldDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setMessage("delete?")
            .setPositiveButton("yes") { _, _ ->
                setFragmentResult("deleteFieldKey", requireArguments())
                dismiss()
            }
            .setNegativeButton("no") { _, _ ->
                dismiss()
            }
            .create()
}