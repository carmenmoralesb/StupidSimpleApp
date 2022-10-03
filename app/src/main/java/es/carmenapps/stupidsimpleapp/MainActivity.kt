package es.carmenapps.stupidsimpleapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import dagger.hilt.android.AndroidEntryPoint
import es.carmenapps.stupidsimpleapp.databinding.ActivityMainBinding
import es.carmenapps.stupidsimpleapp.ui.utils.setVisible

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

  private var executeMenuClick: () -> Boolean = {
    false
  }

  private var binding: ActivityMainBinding? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding?.root)
  }

  override fun onResume() {
    super.onResume()
    setSupportActionBar(binding?.mainToolbarAppToolbar)
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_list, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
      R.id.order_by_id -> executeMenuClick()
      else -> super.onOptionsItemSelected(item)
    }
  }

  override fun onDestroy() {
    binding = null
    super.onDestroy()
  }

  fun setOnClickMenuListener(onClickMenu: () -> Boolean) {
    executeMenuClick = onClickMenu
  }

  fun setLoading(loading: Boolean, loadingText: String) {
    binding?.mainContainerLoading?.loadingContainerMain.setVisible(loading)
    binding?.mainContainerLoading?.loadingLabelLoadingText?.text = loadingText
  }
}