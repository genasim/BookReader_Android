package genadimk.bookreader.ui.floatingButton

import android.view.View
import com.google.android.material.floatingactionbutton.FloatingActionButton

class AppFloatingButton {
    companion object {
        val buttonRemover: ButtonRemove = ButtonRemove()
        val buttonAdder: ButtonAdd = ButtonAdd()

        var button: FloatingActionButton? = null
            set(value) {
                if (field == null) field = value
                else return
            }

        var buttonHandler: ButtonHandler = ButtonAdd()
            set(value) {
                field = value
                button?.setImageResource(value.imageRes)
                button?.setOnClickListener {
                    value.clickButton()
                }
            }

        fun disable() {
            button?.visibility = View.GONE
        }

        fun enable() {
            button?.visibility = View.VISIBLE
        }
    }
}