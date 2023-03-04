package genadimk.bookreader.view.dialog

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import genadimk.bookreader.R

class NoCurrentBookDialog(private val callback: () -> Unit) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog = activity?.let {
        MaterialAlertDialogBuilder(requireActivity())
            .setTitle(R.string.alert_no_current_book_title)
            .setPositiveButton(R.string.alert_no_current_book_ok) { _, _ -> callback() }
            .setCancelable(true)
            .setMessage(R.string.alert_no_current_book_message)
            .create()
    } ?: throw IllegalStateException("Activity cannot be null")

    override fun onCancel(dialog: DialogInterface) {
        callback()
    }

}