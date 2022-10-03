package es.carmenapps.stupidsimpleapp.data.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import es.carmenapps.stupidsimpleapp.R
import es.carmenapps.stupidsimpleapp.data.vo.UserVO
import es.carmenapps.stupidsimpleapp.databinding.RowFragmentUserListBinding
import timber.log.Timber

class UserListAdapter(
  private val listener: (UserListAdapterAction) -> Unit,
) : ListAdapter<UserVO, RecyclerView.ViewHolder>(UserListDiffUtil()) {
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
    val binding = RowFragmentUserListBinding.inflate(
      LayoutInflater.from(parent.context),
      parent,
      false
    )
    return UserListHolder(binding)
  }


  override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
    when (viewHolder) {
      is UserListHolder -> {
        viewHolder.bind(
          getItem(position) as UserVO,
          listener
        )
      }
    }
  }

  inner class UserListHolder(private val binding: RowFragmentUserListBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
      item: UserVO,
      listener: (UserListAdapterAction) -> Unit
    ) {
      item.name.also { binding.rowUserNamePlaceholder.text = it }
      item.birthDay.also { binding.rowUserBirthdayPlaceholder.text = it }
      item.id.also { binding.rowUserIdPlaceholder.text = it }


      Glide.with(itemView.context)
        .load(item.photo)
        .placeholder(R.drawable.ic_no_profile_picture_icon)
        .into(binding.rowUserPhoto)

      binding.root.setOnClickListener {
        listener(
          UserListAdapterAction.DetailAction(
            item.id
          )
        )
      }

      binding.addUserSaveBtn.setOnClickListener() {
        listener(
          UserListAdapterAction.RemoveAction(
            item.id,
            item.name,
            item.birthDay
          )
        )
      }

      binding.root.setOnLongClickListener {
        listener(
          UserListAdapterAction.ModifyAction(
            item.id,
            item.name,
            item.birthDay
          )
        )
        true
      }

    }
  }


  class UserListDiffUtil : DiffUtil.ItemCallback<UserVO>() {
    override fun areItemsTheSame(oldItem: UserVO, newItem: UserVO): Boolean =
      oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: UserVO, newItem: UserVO): Boolean =
      oldItem == newItem

  }
}