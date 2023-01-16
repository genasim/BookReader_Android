package genadimk.bookreader.ui.floatingButton

import com.google.common.truth.Truth.assertThat
import genadimk.bookreader.ui.home.booklist.Book
import genadimk.bookreader.ui.home.booklist.TestBookAdapter
import org.junit.Before
import org.junit.Test

class ButtonRemoveTest {

    private lateinit var buttonRemove: ButtonRemove
    private lateinit var data: MutableList<Book>

    @Before
    fun setUp() {
        data = mutableListOf(Book(), Book(), Book(), Book(), Book(), Book())
        buttonRemove = ButtonRemove(data)
        buttonRemove.adapter = TestBookAdapter(data)
    }

    @Test
    fun remove_checked_items() {
        val initialDataSize = data.size

        buttonRemove.data[0].isChecked = true
        buttonRemove.data[1].isChecked = true
        buttonRemove.data[2].isChecked = true

        buttonRemove.removeCheckedItems()
        val dataSize = data.size

        assertThat(initialDataSize).isEqualTo(dataSize)
        assertThat(AppFloatingButton.buttonHandler).isInstanceOf(ButtonAdd::class.java)
    }

    @Test
    fun remove_single_item() {
        val itemToRemove = buttonRemove.data[3]
        val initialDataSize = buttonRemove.adapter.itemCount

        buttonRemove.removeItem(itemToRemove)

        assertThat(initialDataSize).isEqualTo(buttonRemove.adapter.itemCount)
    }
}