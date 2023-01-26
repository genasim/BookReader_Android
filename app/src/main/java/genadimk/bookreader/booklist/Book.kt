package genadimk.bookreader.booklist

import com.google.android.material.card.MaterialCardView
import genadimk.bookreader.R

data class Book(
    val bookCover: Int = R.drawable.ic_launcher_background,
    val bookName: String = "Clean code",
    val bookAuthor: String = "Author",
) {
    var bookCard: MaterialCardView? = null
    var isChecked: Boolean = false
}
