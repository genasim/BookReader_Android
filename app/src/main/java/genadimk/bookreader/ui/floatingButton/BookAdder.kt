package genadimk.bookreader.ui.floatingButton

import android.util.Log
import genadimk.bookreader.R
import genadimk.bookreader.TAG


object BookAdder : BookHandler {
    override val imageRes: Int
        get() = R.drawable.ic_add

    override fun clickButton() {
        Log.d(TAG, "clickButton")
    }
}