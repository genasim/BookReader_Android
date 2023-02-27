package genadimk.bookreader.utils

import genadimk.bookreader.booklist.Book
import genadimk.bookreader.model.BookEntry

fun Book.asBookEntry(): BookEntry =
    BookEntry(
        id = this.id,
        name = this.name,
        uri = this.uri.toString(),
        page = this.page,
        current = this.current
    )


fun BookEntry.asBook(): Book =
    Book.Builder(this).build()
