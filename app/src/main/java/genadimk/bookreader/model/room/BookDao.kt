package genadimk.bookreader.model.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {
    @Query("SELECT * FROM books ORDER BY name ASC")
    fun getAllBooks(): Flow<List<BookEntry>>

    @Query("SELECT * FROM books WHERE uri = :uri")
    fun getBook(uri: String): BookEntry?

    @Query("SELECT * FROM books WHERE current = 1")
    fun getCurrentBook(): BookEntry

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(book: BookEntry)

    @Delete
    suspend fun delete(book: BookEntry)

    @Update
    suspend fun update(book: BookEntry)
}