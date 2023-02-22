package genadimk.bookreader.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.material.card.MaterialCardView

@Entity(tableName = "books")
data class Books(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @NonNull @ColumnInfo(name = "name")
    val name: String,
    @NonNull @ColumnInfo(name = "uri")
    val uri: String,
    @NonNull @ColumnInfo(name = "page", defaultValue = "0")
    val page: Int,
    @NonNull @ColumnInfo(name = "checked", defaultValue = "0")
    val checked: Int
)
