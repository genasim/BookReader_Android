package genadimk.bookreader.booklist

import android.net.Uri
import androidx.annotation.DrawableRes
import com.google.android.material.card.MaterialCardView
import genadimk.bookreader.R

class Book private constructor(
    val cover: Int,
    val name: String,
    val author: String,
    val content: Map<Int, String>,
    val uri: Uri
) {
    var card: MaterialCardView? = null
    var isChecked: Boolean = false

    lateinit var pages: Map<Int, String>

    class Builder {
        companion object {
            private var cover: Int = R.drawable.ic_launcher_background
            private var name: String = "Clean code"
            private var author: String = "Author"
            private var content: Map<Int, String> = mapOf()
            private var uri: Uri =
                Uri.Builder().path("file:///android_asset/Intro in Programing.pdf").build()

            fun cover(@DrawableRes bookCover: Int) =
                apply { cover = bookCover }

            fun name(bookName: String) =
                apply { name = bookName }

            fun author(bookAuthor: String) =
                apply { author = bookAuthor }

            fun content(bookContent: Map<Int, String>) =
                apply { content = bookContent }

            fun uri(bookUri: Uri) =
                apply { uri = bookUri }

            fun build() = Book(cover, name, author, content, uri)
        }
    }
}
