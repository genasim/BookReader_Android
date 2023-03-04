package genadimk.bookreader.view.dialog

import android.app.Dialog
import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import genadimk.bookreader.R

class EditBoxDialog(private val callback: (TextView?) -> Unit) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog = activity?.let {
        MaterialAlertDialogBuilder(it)
            .setTitle(R.string.alert_edit_item_title)
            .setCancelable(true)
            .setView(R.layout.dialog_edit_item)
            .setPositiveButton(R.string.alert_edit_item_yes_button) { _, _ ->
                val editText by lazy { dialog?.findViewById<TextInputEditText>(R.id.edit_item_field) }
                callback(editText)
            }
            .setNeutralButton(R.string.confirm_remove_cancel_button) { _, _ -> }
            .create()

    } ?: throw IllegalStateException("Activity cannot be null")

}