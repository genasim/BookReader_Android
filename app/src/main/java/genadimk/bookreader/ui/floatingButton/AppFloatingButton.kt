package genadimk.bookreader.ui.floatingButton

import com.google.android.material.floatingactionbutton.FloatingActionButton

class AppFloatingButton {
    companion object {
        var button: FloatingActionButton? = null
            set(value) {
                if (field == null) field = value
                else return
            }

        fun updateButton(newHandler: ButtonHandler) {
            button?.setImageResource(newHandler.imageRes)
            button?.setOnClickListener {
                newHandler.clickButton()
            }
        }
    }
}