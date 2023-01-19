package genadimk.bookreader.booklist

import com.google.android.material.card.MaterialCardView
import genadimk.bookreader.R

data class Book(
    val bookCover: Int = R.drawable.ic_launcher_background,
    val bookName: String = "Clean code",
    val bookAuthor: String = "Author",
    var isChecked: Boolean = false
) {
    var bookCard: MaterialCardView? = null
}

object BookDataList {
    val data = mutableListOf(
        Book(bookName = "11111"),
        Book(bookName = "22222"),
        Book(bookName = "33333"),
        Book(bookName = "44444")
    )

    var currentBook: Book = data[0]
}