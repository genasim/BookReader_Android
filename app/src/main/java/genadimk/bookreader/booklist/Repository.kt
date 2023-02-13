package genadimk.bookreader.booklist

interface Repository {
    fun getRepository(): List<Book>

    /** Adds an item to the list and returns it's index */
    fun addItem(item: Book): Int

    /** Removers an item from the list and returns it's index */
    fun removeItem(item: Book): Int
}