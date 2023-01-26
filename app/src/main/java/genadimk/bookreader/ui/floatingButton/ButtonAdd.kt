package genadimk.bookreader.ui.floatingButton

import androidx.recyclerview.widget.RecyclerView
import genadimk.bookreader.R
import genadimk.bookreader.observer.Broadcaster
import genadimk.bookreader.observer.Observable

class ButtonAdd :
    ButtonHandler {

    companion object : Observable by Broadcaster()

    lateinit var adapter: RecyclerView.Adapter<*>

    override val imageRes: Int
        get() = R.drawable.ic_add

    override fun clickButton() {
        addItem()
    }

    private fun addItem() {
        sendUpdateEvent()
    }
}