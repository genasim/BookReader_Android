package genadimk.bookreader.booklist

import android.net.Uri
import androidx.annotation.DrawableRes
import com.google.android.material.card.MaterialCardView
import genadimk.bookreader.R

class Book private constructor(
    val cover: Int,
    val name: String,
    val uri: Uri,
) {
    var card: MaterialCardView? = null
    var isChecked: Boolean = false

    var page = 0

    class Builder {
        private var cover: Int = R.drawable.ic_launcher_background
        private var name: String = "Clean code"
        private var uri: Uri =
            Uri.Builder().path("Intro in Programing.pdf").build()

        fun cover(@DrawableRes bookCover: Int) =
            apply { cover = bookCover }

        fun name(bookName: String) =
            apply { name = bookName }

        fun uri(bookUri: Uri) =
            apply { uri = bookUri }

        fun build() = Book(cover, name, uri)
    }
}
