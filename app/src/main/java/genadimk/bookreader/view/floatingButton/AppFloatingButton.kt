package genadimk.bookreader.view.floatingButton

import android.view.View
import androidx.activity.result.ActivityResultLauncher
import com.google.android.material.floatingactionbutton.FloatingActionButton
import genadimk.bookreader.viewmodels.HomeViewModel
import kotlin.reflect.KFunction1

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

        fun enable(viewModel: HomeViewModel, contentPicker: ActivityResultLauncher<Array<String>>, confirmFunc: KFunction1<() -> Unit, Unit>) {
            button?.visibility = View.VISIBLE

            buttonAdder = ButtonAdd(contentPicker)
            buttonRemover = ButtonRemove(viewModel, confirmFunc)
        }

        fun disable() {
            button?.visibility = View.GONE
        }
    }
}