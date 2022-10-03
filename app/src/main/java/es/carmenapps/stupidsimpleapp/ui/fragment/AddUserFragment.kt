package es.carmenapps.stupidsimpleapp.ui.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast.LENGTH_SHORT
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import es.carmenapps.stupidsimpleapp.R
import es.carmenapps.stupidsimpleapp.databinding.FragmentAddUserBinding
import es.carmenapps.stupidsimpleapp.ui.viewmodel.UserListViewModel
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class AddUserFragment : BaseFragment() {

  private lateinit var binding: FragmentAddUserBinding
  private lateinit var navController: NavController
  private val viewModel: UserListViewModel by viewModels()

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    navController = findNavController()
    binding = FragmentAddUserBinding.bind(
      inflater.inflate(
        R.layout.fragment_add_user,
        container,
        false
      )
    )
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    viewModel.init()
  }

  override fun onResume() {
    super.onResume()
    val cal = Calendar.getInstance()

    val year = cal.get(Calendar.YEAR)
    val month = cal.get(Calendar.MONTH)
    val day = cal.get(Calendar.DAY_OF_MONTH)

    val timestampFormat: DateFormat = SimpleDateFormat("HH:mm:ss")
    val date = Date()
    val hourFormatted: String = timestampFormat.format(date)

    println(hourFormatted)
    binding.addUserDatePlaceholder.setEndIconOnClickListener {
      val datePickerDialog = context?.let { it1 ->
        DatePickerDialog(
          it1,
          { _, day, month, year ->
            binding.addUserDatePlaceholder.hint = "$year-$month-${day}T${hourFormatted}"
          },
          year,
          month,
          day,
        )
      }
      datePickerDialog?.show()
    }

    binding.addUserSaveBtn.setOnClickListener {
      view?.let { it1 -> context?.hideKeyboard(it1) }

      viewModel.addUser(
        binding.addUserNamePlaceholder.editText?.text.toString(),
        binding.addUserDatePlaceholder.hint.toString()
      )

      view?.let { it1 -> Snackbar.make(it1, R.string.user_add__succesful_dialog, LENGTH_SHORT) }
        ?.show()
      setLoading(true)
      navigateBack()
    }
  }

  private fun navigateBack() {
    findNavController().navigateUp()
  }
}