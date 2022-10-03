package es.carmenapps.stupidsimpleapp.ui.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import es.carmenapps.stupidsimpleapp.R
import es.carmenapps.stupidsimpleapp.data.adapter.UserListAdapter
import es.carmenapps.stupidsimpleapp.data.adapter.UserListAdapterAction
import es.carmenapps.stupidsimpleapp.data.adapter.UserListState
import es.carmenapps.stupidsimpleapp.data.bo.OrderFilter
import es.carmenapps.stupidsimpleapp.data.vo.UserVO
import es.carmenapps.stupidsimpleapp.databinding.FragmentListUserBinding
import es.carmenapps.stupidsimpleapp.ui.utils.hide
import es.carmenapps.stupidsimpleapp.ui.utils.setVisible
import es.carmenapps.stupidsimpleapp.ui.utils.show
import es.carmenapps.stupidsimpleapp.ui.viewmodel.UserListViewModel

@AndroidEntryPoint
class UserListFragment : BaseFragment() {

  private lateinit var binding: FragmentListUserBinding
  private lateinit var navController: NavController

  private val viewModel: UserListViewModel by viewModels()
  private val adapter by lazy {
    UserListAdapter(::adapterListener)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setHasOptionsMenu(true)
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    navController = findNavController()
    binding = FragmentListUserBinding.bind(
      inflater.inflate(
        R.layout.fragment_list_user,
        container,
        false
      )
    )
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initRecycler()
    initViewModel()
    binding.userListBtnAdd.setOnClickListener {
      navController.navigate(
        UserListFragmentDirections.actionMainFragmentToUserAddFragment()
      )
    }
  }

  private fun initRecycler() {
    binding.userListRv.adapter = this.adapter
    binding.userListRv.layoutManager = LinearLayoutManager(context);
  }


  private fun updateViews(userList: List<UserVO>) {
    binding.userListContainerMainScroll.setVisible(userList.isNotEmpty())
    binding.userListLabelError.setVisible(userList.isEmpty())
    binding.userListBtnRetry.setVisible(userList.isEmpty())
  }


  private fun initViewModel() {
    viewModel.userListState.observe(viewLifecycleOwner) { state ->
      when (state) {
        UserListState.Error -> {
          binding.apply {
            binding.userListContainerMainScroll.hide()
            binding.userListLabelError.show()
            binding.userListBtnRetry.show()
          }
          setLoading(false)

          showUsersErrorDialog(
            getString(
              R.string.error__unknown
            )
          )
          binding.userListLabelError.setVisible(true)
        }
        UserListState.Loading -> {
          binding.userListContainerMainScroll.hide()
          setLoading(
            true,
            getString(R.string.user_list__loading_message)
          )
        }
        is UserListState.Render -> {
          setLoading(false)
          handleRender(state.users)
          updateViews(state.users)
        }
      }
    }
    viewModel.init()
  }

  private fun handleRender(user: List<UserVO>) {
    adapter.submitList(user)
  }

  override fun onPause() {
    super.onPause()
  }

  override fun onDetach() {
    super.onDetach()
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.order_by_name -> {
        viewModel.orderByFilter(OrderFilter.BY_NAME)
      }

      R.id.order_by_id -> {
        viewModel.orderByFilter(OrderFilter.BY_ID)
      }

      R.id.reload -> {
        viewModel.init()
      }
    }
    return false
  }

  private fun adapterListener(actions: UserListAdapterAction) {
    when (actions) {
      is UserListAdapterAction.DetailAction -> {
        navController.navigate(
          UserListFragmentDirections.actionMainFragmentToUserDetailFragment(
            actions.id
          )
        )
      }
      is UserListAdapterAction.RemoveAction -> {
        showRemoveDialog(actions.id.toInt())
      }
      is UserListAdapterAction.ModifyAction -> {
      }
    }
  }

  private fun showUsersErrorDialog(errorMessage: String) {
    val dialog = AlertDialog.Builder(context)
    dialog.setTitle(R.string.error_dialog__title)
    dialog.setMessage(errorMessage)
    dialog.setPositiveButton(R.string.error_dialog__accept) { dialogInterface, _ ->
      dialogInterface.dismiss()
    }

    dialog.setNegativeButton(R.string.error_dialog__retry) { dialogInterface, _ ->
      viewModel.init()
      dialogInterface.dismiss()
    }
    dialog.show()
  }

  private fun showRemoveDialog(id: Int) {
    val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
    builder.let {
      it.setMessage(getString(R.string.user_list__delete_dialog))
        .setTitle(getString(R.string.user_list__delete_dialog_delete_user))
        .setPositiveButton(getString(R.string.user_list__delete_dialog_yes)) { _, _ ->
          viewModel.deleteUser(id)
        }
        .setNegativeButton(getString(R.string.user_list__delete_dialog_no)) { _, _ ->
          //no-op
        }
    }
    val dialog: AlertDialog = builder.create()
    dialog.show()
  }
}