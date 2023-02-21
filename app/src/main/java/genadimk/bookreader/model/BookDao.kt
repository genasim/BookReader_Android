package genadimk.bookreader.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {
    @Query("SELECT * FROM books ORDER BY name ASC")
    fun getAllBooks(): Flow<List<Books>>

    @Query("SELECT * from books WHERE id = :id")
    fun getBook(id: Int): Flow<Books>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(book: Books)

    @Delete
    suspend fun delete(book: Books)

    @Update
    suspend fun update(book: Books)
}