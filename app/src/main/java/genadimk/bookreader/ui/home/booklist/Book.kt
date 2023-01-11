package genadimk.bookreader.ui.home.booklist

import genadimk.bookreader.R

data class Book (
    val bookCover: Int = R.drawable.ic_launcher_foreground,
    val bookName: String = "Clean code",
    val bookAuthor: String = "Author"
)


object BookDataList {
    val data: List<Book> =  mutableListOf(
        Book(), Book(), Book()
    )
}