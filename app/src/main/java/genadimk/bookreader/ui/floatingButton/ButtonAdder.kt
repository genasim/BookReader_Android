package genadimk.bookreader.ui.floatingButton

import genadimk.bookreader.R
import genadimk.bookreader.observer.Broadcaster
import genadimk.bookreader.observer.Observable

object ButtonAdder :
    ButtonHandler,
    Observable by Broadcaster() {
    override val imageRes: Int
        get() = R.drawable.ic_add

    override fun clickButton() {
        sendUpdateEvent()
    }
}