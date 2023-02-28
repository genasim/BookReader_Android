package genadimk.bookreader.model

import android.net.Uri
import androidx.annotation.DrawableRes
import com.google.android.material.card.MaterialCardView
import genadimk.bookreader.R
import genadimk.bookreader.model.room.BookEntry

class Book private constructor(
    bookEntry: BookEntry,
    val cover: Int,
) {
    val id: Int = bookEntry.id
    val name: String = bookEntry.name
    val uri: Uri = Uri.parse(bookEntry.uri)
    var current = bookEntry.current
    var page = bookEntry.page

    var card: MaterialCardView? = null
    var isChecked: Boolean = false

    class Builder(private val bookEntry: BookEntry) {
        private var cover: Int = R.drawable.ic_launcher_background

        fun cover(@DrawableRes bookCover: Int) =
            apply { cover = bookCover }

        fun build(): Book = Book(bookEntry, cover)
    }
}
