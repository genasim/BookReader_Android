package genadimk.bookreader.ui.floatingButton

import genadimk.bookreader.R
import genadimk.bookreader.observer.Broadcaster
import genadimk.bookreader.observer.Observable

object ButtonRemover :
    ButtonHandler,
    Observable by Broadcaster() {

    override val imageRes: Int
        get() = R.drawable.ic_delete

    override fun clickButton() {
        sendUpdateEvent()
        AppFloatingButton.updateButton(ButtonAdder)
    }

}