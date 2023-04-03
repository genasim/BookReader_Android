package genadimk.bookreader.view.floatingButton

import androidx.activity.result.ActivityResultLauncher
import genadimk.bookreader.R

class ButtonAdd(private val contentPicker: ActivityResultLauncher<Array<String>>)
    : ButtonHandler {

    override val imageRes: Int
        get() = R.drawable.ic_add

    override fun clickButton() {
        addItem()
    }

    private fun addItem() {
        contentPicker.launch(arrayOf("application/pdf"))
    }
}