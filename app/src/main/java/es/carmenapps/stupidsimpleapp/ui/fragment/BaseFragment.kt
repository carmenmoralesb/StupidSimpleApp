package es.carmenapps.stupidsimpleapp.ui.fragment

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import es.carmenapps.stupidsimpleapp.MainActivity
import es.carmenapps.stupidsimpleapp.ui.utils.AppConstants.EMPTY_TEXT

abstract class BaseFragment : Fragment() {

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    (activity as? MainActivity)?.setOnClickMenuListener {
      onFilterMenuButtonClickedListener()
    }
  }

  open fun onFilterMenuButtonClickedListener(): Boolean {
    return false
  }

  fun navigateTo(action: NavDirections) {
    findNavController().navigate(action)
  }

  fun setLoading(loading: Boolean, loadingText: String) {
    (activity as? MainActivity)?.setLoading(loading, loadingText)
  }

  fun setLoading(loading: Boolean) {
    setLoading(loading, EMPTY_TEXT)
  }

  fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
  }

  fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
  }

  fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
  }
}