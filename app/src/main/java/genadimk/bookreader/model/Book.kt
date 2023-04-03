package genadimk.bookreader.model

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import com.google.android.material.card.MaterialCardView
import genadimk.bookreader.model.room.BookEntry
import genadimk.bookreader.utils.FrontPageFactory

/**
 * Wrapper object for [BookEntry], meant to hold all non-persistent
 * data for the books. This class should be used for book-management
 * apart from book-creation
 * */
class Book constructor(
    bookEntry: BookEntry,
    context: Context,
) {
    val id: Int = bookEntry.id
    val name: String = bookEntry.name
    val uri: Uri = Uri.parse(bookEntry.uri)
    val cover: Bitmap = FrontPageFactory(context).produceBitmap(bookEntry.uri)
    var current = bookEntry.current
    var page = bookEntry.page

    var card: MaterialCardView? = null
    var isChecked: Boolean = false
}
