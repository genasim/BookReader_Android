package genadimk.bookreader.view.floatingButton

import android.view.View
import androidx.activity.result.ActivityResultLauncher
import com.google.android.material.floatingactionbutton.FloatingActionButton
import genadimk.bookreader.viewmodels.HomeViewModel

class AppFloatingButton {
    companion object {
        lateinit var buttonRemover: ButtonHandler
        lateinit var buttonAdder: ButtonHandler

        var button: FloatingActionButton? = null
            set(value) {
                if (field == null) field = value
                else return
            }

        var buttonHandler: ButtonHandler? = null
            set(value) {
                field = value ?: return
                button?.apply {
                    setImageResource(value.imageRes)
                    setOnClickListener {
                        value.clickButton()
                    }
                }
            }

        fun init(viewModel: HomeViewModel, contentPicker: ActivityResultLauncher<Array<String>>) {
            buttonAdder = ButtonAdd(contentPicker)
            buttonRemover = ButtonRemove(viewModel)
        }

        fun disable() {
            button?.visibility = View.GONE
        }

        fun enable() {
            button?.visibility = View.VISIBLE
        }
    }
}