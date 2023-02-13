package genadimk.bookreader.booklist

import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

internal class BookRepositoryTest {

    lateinit var data: Repository

    @Before
    fun setUp() {
        data = BookRepository
    }

    @Test
    fun addItem() {
        //  TODO: fix test
//        val index = data.addItem()
//        assertThat(index).isEqualTo(0)
    }

    @Test
    fun removeItem() {
        val initialSize = data.getRepository().size
        val book = data.getRepository()[2]
        data.removeItem(book)

        assertThat(data.getRepository().size).isEqualTo(initialSize - 1)
    }
}