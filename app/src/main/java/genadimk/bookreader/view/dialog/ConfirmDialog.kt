package genadimk.bookreader.view.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import genadimk.bookreader.R

class ConfirmDialog(private val callback: () -> Unit) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog = activity?.let {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.confirm_remove_title)
            .setCancelable(true)
            .setMessage(R.string.confirm_remove_message)
            .setPositiveButton(R.string.confirm_remove_yes_button) { _, _ -> callback() }
            .setNeutralButton(R.string.confirm_remove_cancel_button) { _, _ -> }
            .create()

    } ?: throw IllegalStateException("Activity cannot be null")

}