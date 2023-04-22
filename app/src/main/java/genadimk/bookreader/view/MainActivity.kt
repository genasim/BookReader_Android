package genadimk.bookreader.view

import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.documentfile.provider.DocumentFile
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.asLiveData
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import com.google.android.material.navigation.NavigationView
import genadimk.bookreader.BookReaderApplication
import genadimk.bookreader.R
import genadimk.bookreader.databinding.ActivityMainBinding
import genadimk.bookreader.view.floatingButton.AppFloatingButton
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        AppFloatingButton.button = binding.appBarMain.floatingButton
        AppFloatingButton.refresh()

        val drawerLayout: DrawerLayout = binding.drawerLayout
        navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_readview
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        //  Bind drawer items to their destinations
        val navView: NavigationView = binding.navView
        navView.setupWithNavController(navController)
    }

    override fun onStart() {
        super.onStart()
        checkRepositoryIntegrity()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }

    private fun checkRepositoryIntegrity() {
        val repo = (application as BookReaderApplication).repository
        repo.getBookEntries().asLiveData().observe(this) {
            runBlocking {
                it.forEach { entry ->
                    val uri = Uri.parse(entry.uri)
                    if (!doesFileExist(uri))
                        repo.delete(entry)
                }
            }
        }
    }

    private fun doesFileExist(uri: Uri): Boolean {
        val docFile = DocumentFile.fromSingleUri(this, uri)
        return docFile?.exists()!!
    }
}