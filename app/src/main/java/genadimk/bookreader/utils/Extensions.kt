package genadimk.bookreader.utils

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.OpenableColumns
import com.pdftron.pdf.PDFDoc
import genadimk.bookreader.model.Book
import genadimk.bookreader.model.room.BookEntry
import java.io.File

fun Book.asBookEntry(): BookEntry =
    BookEntry(
        id = this.id,
        name = this.name,
        uri = this.uri.toString(),
        page = this.page,
        current = this.current
    )

fun BookEntry.asBook(context: Context): Book = Book(this, context)

val Any.TAG: String
    get() = javaClass.simpleName

fun Uri.getFilename(contentResolver: ContentResolver): String? {
    return when (scheme) {
        ContentResolver.SCHEME_CONTENT -> {
            contentResolver.query(this, null, null, null, null)?.use { cursor ->
                cursor.moveToFirst()
                val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                cursor.getString(nameIndex).substringBeforeLast('.');
            }
        }
        ContentResolver.SCHEME_FILE -> {
            path?.let { path ->
                File(path).name.substringBeforeLast('.')
            }
        }
        else -> null
    }
}