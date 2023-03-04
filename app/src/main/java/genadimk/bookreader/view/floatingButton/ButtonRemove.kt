package genadimk.bookreader.view.floatingButton

import genadimk.bookreader.R
import genadimk.bookreader.model.Book
import genadimk.bookreader.view.HomeFragment
import genadimk.bookreader.view.dialog.ConfirmDialog
import java.util.function.Predicate
import kotlin.reflect.KFunction1

class ButtonRemove(
    private val fragment: HomeFragment,
) : ButtonHandler {

    override val imageRes: Int
        get() = R.drawable.ic_delete

    override fun clickButton() {
//        confirmFunc.invoke { removeCheckedItems() }
        ConfirmDialog { removeCheckedItems() }.show(
            fragment.parentFragmentManager,
            "Confirm Dialog"
        )
    }

    private fun removeCheckedItems() {
        val predicate = Predicate { bookItem: Book -> bookItem.isChecked }
        fragment.viewModel.getBookList()
            .filter { predicate.test(it) }
            .forEach { removeItem(it) }
        AppFloatingButton.apply { buttonHandler = buttonAdder }
    }

    private fun removeItem(book: Book) {
        fragment.viewModel.removeBook(book)
    }
}