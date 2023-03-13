package genadimk.bookreader.model

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.webkit.URLUtil
import androidx.annotation.DrawableRes
import com.google.android.material.card.MaterialCardView
import com.pdftron.pdf.PDFDoc
import genadimk.bookreader.R
import genadimk.bookreader.model.room.BookEntry
import genadimk.bookreader.utils.TAG

class Book constructor(
    bookEntry: BookEntry,
) {
    val id: Int = bookEntry.id
    val name: String = bookEntry.name
    val uri: Uri = Uri.parse(bookEntry.uri)
    val cover: Int = R.drawable.ic_launcher_background
    var current = bookEntry.current
    var page = bookEntry.page

    var card: MaterialCardView? = null
    var isChecked: Boolean = false

    init {
//        frontPage = null

    }
}
