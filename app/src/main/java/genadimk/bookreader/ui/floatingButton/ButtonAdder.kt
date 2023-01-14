package genadimk.bookreader.ui.floatingButton

import androidx.recyclerview.widget.RecyclerView
import genadimk.bookreader.R
import genadimk.bookreader.observer.Broadcaster
import genadimk.bookreader.observer.Observable

object ButtonAdder :
    ButtonHandler {

    lateinit var adapter: RecyclerView.Adapter<*>

    override val imageRes: Int
        get() = R.drawable.ic_add

    override fun clickButton() {

    }
}