package genadimk.bookreader.view.floatingButton

import android.view.View
import androidx.activity.result.ActivityResultLauncher
import com.google.android.material.floatingactionbutton.FloatingActionButton
import genadimk.bookreader.view.HomeFragment
import genadimk.bookreader.viewmodels.HomeViewModel
import kotlin.reflect.KFunction1

class AppFloatingButton {
    companion object {
        lateinit var buttonRemover: ButtonHandler
        lateinit var buttonAdder: ButtonHandler

        var button: FloatingActionButton? = null

        var buttonHandler: ButtonHandler? = null
            set(value) {
                field = value ?: return
                refresh()
            }

        fun enable(fragment: HomeFragment, contentPicker: ActivityResultLauncher<Array<String>>) {
            button?.visibility = View.VISIBLE

            buttonAdder = ButtonAdd(contentPicker)
            buttonRemover = ButtonRemove(fragment)
        }

        fun disable() {
            button?.visibility = View.GONE
        }

        fun refresh() {
            if (buttonHandler == null)
                return

            button?.apply {
                setImageResource(buttonHandler!!.imageRes)
                setOnClickListener {
                    buttonHandler!!.clickButton()
                }
            }
        }
    }
}