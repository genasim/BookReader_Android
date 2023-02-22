package genadimk.bookreader.view.floatingButton

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import genadimk.bookreader.R
import genadimk.bookreader.booklist.Book
import genadimk.bookreader.booklist.BookListViewAdapter
import genadimk.bookreader.booklist.BookRepository
import genadimk.bookreader.view.HomeFragment

class ButtonAdd :
    ButtonHandler {

    lateinit var adapter: RecyclerView.Adapter<*>

    override val imageRes: Int
        get() = R.drawable.ic_add

    override fun clickButton() {
//        requestPermission()
        HomeFragment.contentPicker.launch("application/pdf")
    }

    private fun addItem(newBook: Book) {
        with(adapter as BookListViewAdapter) {
            notifyItemInserted(BookRepository.addItem(newBook))
        }
    }

    private fun requestPermission() {
        val context = (adapter as BookListViewAdapter).parent.context
        when {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.MANAGE_DOCUMENTS
            ) == PackageManager.PERMISSION_GRANTED -> {
                //  Permission has been granted
                HomeFragment.contentPicker.launch("application/pdf")
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                context as Activity,
                Manifest.permission.MANAGE_DOCUMENTS
            ) -> {
            }

            else -> HomeFragment.permissionRequest.launch(Manifest.permission.MANAGE_DOCUMENTS)
        }
    }
}