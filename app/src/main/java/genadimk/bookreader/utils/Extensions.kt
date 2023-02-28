package genadimk.bookreader.utils

import android.content.ContentResolver
import android.net.Uri
import android.provider.OpenableColumns
import genadimk.bookreader.booklist.Book
import genadimk.bookreader.model.BookEntry
import java.io.File

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


val Any.TAG: String
    get() = javaClass.simpleName

fun getFilename(contentResolver: ContentResolver, uri: Uri): String? {
    return when (uri.scheme) {
        ContentResolver.SCHEME_CONTENT -> {
            contentResolver.query(uri, null, null, null, null)?.use { cursor ->
                cursor.moveToFirst()
                val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                cursor.getString(nameIndex);
            }
        }
        ContentResolver.SCHEME_FILE -> {
            uri.path?.let { path ->
                File(path).name
            }
        }
        else -> null
    }
}