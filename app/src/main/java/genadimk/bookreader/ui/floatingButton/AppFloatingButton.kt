package genadimk.bookreader.ui.floatingButton

import com.google.android.material.floatingactionbutton.FloatingActionButton

class AppFloatingButton {
    companion object {
        val buttonRemover: ButtonRemove = ButtonRemove()
        val buttonAdder: ButtonAdd = ButtonAdd()

        var buttonHandler: ButtonHandler = ButtonAdd()
            set(value) {
                field = value
                button?.setImageResource(value.imageRes)
                button?.setOnClickListener {
                    value.clickButton()
                }
            }

        var button: FloatingActionButton? = null
            set(value) {
                if (field == null) field = value
                else return
            }
    }
}