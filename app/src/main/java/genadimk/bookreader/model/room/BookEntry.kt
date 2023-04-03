package genadimk.bookreader.model.room

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Base book object that is stored in database as a record
 * */
@Entity(tableName = "books")
data class BookEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @NonNull @ColumnInfo(name = "name")
    val name: String,
    @NonNull @ColumnInfo(name = "uri")
    val uri: String,
    @NonNull @ColumnInfo(name = "page", defaultValue = "0")
    val page: Int,
    @NonNull @ColumnInfo(name = "current", defaultValue = "0")
    val current: Int,
)
