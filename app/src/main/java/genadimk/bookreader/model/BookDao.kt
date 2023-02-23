package genadimk.bookreader.model

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {
    @Query("SELECT * FROM books ORDER BY name ASC")
    fun getAllBooks(): Flow<List<BookEntry>>

    @Query("SELECT * from books WHERE id = :id")
    fun getBook(id: Int): Flow<BookEntry>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(book: BookEntry)

    @Delete
    suspend fun delete(book: BookEntry)

    @Update
    suspend fun update(book: BookEntry)
}