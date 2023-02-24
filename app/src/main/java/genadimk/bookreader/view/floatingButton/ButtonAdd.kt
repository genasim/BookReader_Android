package genadimk.bookreader.view.floatingButton

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import genadimk.bookreader.R
import genadimk.bookreader.booklist.Book
import genadimk.bookreader.booklist.BookListViewAdapter
import genadimk.bookreader.view.HomeFragment

class ButtonAdd(private val contentPicker: ActivityResultLauncher<String>) :
    ButtonHandler {

    lateinit var adapter: RecyclerView.Adapter<*>

    override val imageRes: Int
        get() = R.drawable.ic_add

    override fun clickButton() {
        addItem()
    }

    private fun addItem() {
        contentPicker.launch("application/pdf")
    }

    private fun requestPermission() {
        val context = (adapter as BookListViewAdapter).parent.context
        when {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.MANAGE_DOCUMENTS
            ) == PackageManager.PERMISSION_GRANTED -> {
                //  Permission has been granted
                addItem()
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