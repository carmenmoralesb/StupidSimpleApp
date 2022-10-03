package es.carmenapps.stupidsimpleapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import es.carmenapps.stupidsimpleapp.R
import es.carmenapps.stupidsimpleapp.data.adapter.UserDetailState
import es.carmenapps.stupidsimpleapp.data.constants.AppConstants.DATE_FORMAT
import es.carmenapps.stupidsimpleapp.data.vo.UserVO
import es.carmenapps.stupidsimpleapp.databinding.FragmentUserDetailBinding
import es.carmenapps.stupidsimpleapp.ui.utils.AppConstants.EMPTY_TEXT
import es.carmenapps.stupidsimpleapp.ui.utils.setVisible
import es.carmenapps.stupidsimpleapp.ui.utils.transformIntoDatePicker
import es.carmenapps.stupidsimpleapp.ui.viewmodel.UserDetailViewModel
import java.util.*

@AndroidEntryPoint
class UserDetailFragment : BottomSheetDialogFragment() {

  private val args: UserDetailFragmentArgs by navArgs()

  private val viewModel: UserDetailViewModel by viewModels()

  private lateinit var binding: FragmentUserDetailBinding

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = FragmentUserDetailBinding.bind(
      inflater.inflate(
        R.layout.fragment_user_detail,
        container,
        false
      )
    )
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initViewModel()
  }

  private fun handleRender(user: UserVO) {
    Glide.with(requireContext())
      .load(user.photo)
      .placeholder(R.drawable.ic_no_profile_picture_icon)
      .into(binding.userDetailImageUserImage)
    binding.userDetailLabelFullName.text = user.name
    binding.userDetailBirthday.text = user.birthDay

    binding.userDetailLabelFullName.setVisible(true)

    binding.userDetailBtnClose.setOnClickListener {
      navigateBack()
    }

    binding.addUserSaveBtn.setOnClickListener {
      binding.addUserSaveBtn.text = getString(R.string.user_detail__save_user_info_btn)
      binding.userDetailLabelFullName.setVisible(false)
      binding.userDetailEditPlaceholder.setVisible(true)
      binding.userDetailBirthday.setVisible(false)
      binding.userDetailEditBdayPlaceholder.setVisible(true)
      binding.userDetailEditBdayPlaceholder.hint = user.birthDay
      context.let {
        binding.userDetailEditBdayPlaceholder.transformIntoDatePicker(
          context,
          DATE_FORMAT
        )
      }

      binding.addUserSaveBtn.setOnClickListener {
        val userName = if (binding.userDetailEditPlaceholder.text.toString().isEmpty().not()) {
          binding.userDetailEditPlaceholder.text.toString()
        } else {
          user.name
        }

        val bDay = if (binding.userDetailEditBdayPlaceholder.text.toString().isEmpty().not()) {
          binding.userDetailEditBdayPlaceholder.text.toString()
        } else {
          user.birthDay
        }

        viewModel.modifyUser(
          user.id.toInt(),
          userName,
          bDay
        )

        binding.userDetailLabelFullName.text = userName
        binding.userDetailBirthday.text = bDay

        binding.userDetailLabelFullName.setVisible(true)
        binding.userDetailEditPlaceholder.setVisible(false)
        binding.userDetailBirthday.setVisible(true)
        binding.userDetailEditBdayPlaceholder.setVisible(false)

      }
      binding.userDetailEditPlaceholder.hint = user.name
    }
  }

  private fun navigateBack() {
    findNavController().navigateUp()
  }

  private fun setLoading(visible: Boolean, loadingText: String) {
    binding.userDetailIncludeLoading.apply {
      root.setVisible(visible)
      loadingLabelLoadingText.text = loadingText
    }
  }

  private fun setLoading(visible: Boolean) {
    setLoading(visible, EMPTY_TEXT)
  }

  private fun initViewModel() {
    viewModel.userDetailState.observe(viewLifecycleOwner) { state ->
      when (state) {
        UserDetailState.Error -> {
          setLoading(false)
        }
        UserDetailState.Loading -> {
          context?.getString(R.string.user_list__loading_message)?.let { setLoading(true, it) }
        }
        is UserDetailState.Render -> {
          setLoading(false)
          handleRender(state.user)
        }
      }
    }
    viewModel.init(args.ID)
  }
}