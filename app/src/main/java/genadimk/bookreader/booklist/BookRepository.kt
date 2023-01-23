package genadimk.bookreader.booklist

object BookRepository {
    val data = mutableListOf(
        Book(bookName = "11111"),
        Book(bookName = "22222"),
        Book(bookName = "33333"),
        Book(bookName = "44444")
    )

    var currentBook: Book = data[0]


    @Deprecated("Temporary usage for new items' name; items will have different names regardless")
    private var count: Int = 0

    fun addBook(): Int {
        val item = Book(bookName = count++.toString())
        data.add(0, item)
        return data.indexOf(item)
    }

    fun removeBook(item: Book): Int {
        val position = data.indexOf(item)
        data.remove(item)
        return position
    }
}