package app.appworks.school.publisher

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import app.appworks.school.publisher.ext.getVmFactory
import app.appworks.school.publisher.home.HomeFragment
import app.appworks.school.publisher.home.HomeViewModel
import app.appworks.school.publisher.util.Logger
import com.google.firebase.firestore.FirebaseFirestore

/**
 * Created by Wayne Chen on 2020-01-15.
 */
class MainActivity : AppCompatActivity() {

    /**
     * Lazily initialize our [MainViewModel].
     */
    val viewModel by viewModels<MainViewModel> { getVmFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}