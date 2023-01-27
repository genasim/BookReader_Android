package genadimk.bookreader.booklist

import androidx.annotation.DrawableRes
import com.google.android.material.card.MaterialCardView
import genadimk.bookreader.R

class Book private constructor(
    val cover: Int,
    val name: String,
    val author: String,
    val content: Map<Int, String>
) {

    class Builder {
        private var cover: Int = R.drawable.ic_launcher_background
        private var name: String = "Clean code"
        private var author: String = "Author"
        private var content: Map<Int, String> = mapOf()

        fun cover(@DrawableRes bookCover: Int) =
            apply { cover = bookCover }

        fun name(bookName: String) =
            apply { name = bookName }

        fun author(bookAuthor: String) =
            apply { author = bookAuthor }

        fun content( bookContent: Map<Int, String> ) =
            apply { content = bookContent }

        fun build() = Book(cover, name, author, content)
    }

    var card: MaterialCardView? = null
    var isChecked: Boolean = false

    lateinit var pages: Map<Int, String>
}
