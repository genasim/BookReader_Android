package genadimk.bookreader.ui.home.booklist

import com.google.android.material.card.MaterialCardView
import genadimk.bookreader.R

data class Book (
    val bookCover: Int = R.drawable.ic_launcher_background,
    val bookName: String = "Clean code",
    val bookAuthor: String = "Author",
) {
    var bookCard: MaterialCardView? = null
}


object BookDataList {
    val data: List<Book> =  mutableListOf(
        Book(), Book(), Book()
    )
}