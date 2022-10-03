package es.carmenapps.stupidsimpleapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.carmenapps.stupidsimpleapp.data.Mappers.toVo
import es.carmenapps.stupidsimpleapp.data.adapter.UserListState
import es.carmenapps.stupidsimpleapp.data.bo.OrderFilter
import es.carmenapps.stupidsimpleapp.data.bo.UserBO
import es.carmenapps.stupidsimpleapp.data.remote.dto.UserDTO
import es.carmenapps.stupidsimpleapp.data.repository.Repository
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
  private val repository: Repository,
) : ViewModel() {


  private val _userListState = MutableLiveData<UserListState>()
  val userListState: LiveData<UserListState>
    get() = _userListState

  private var users = mutableListOf<UserBO>()

  fun init() {
    _userListState.postValue(UserListState.Loading)
    getUsers()
  }

  private fun getUsers() {
    viewModelScope.launch {
      users = repository.getAllUserList().toMutableList()
      if (users.isNotEmpty()) {
        _userListState.postValue(UserListState.Render(users.map { it.toVo() }))
      } else {
        _userListState.postValue(UserListState.Error)
      }
    }
  }

  fun orderByFilter(orderFilter: OrderFilter) {
    users = when (orderFilter) {
      OrderFilter.BY_NAME -> {
        users.toList().sortedBy { it.name.lowercase(Locale.getDefault()) }.toMutableList()
      }
      OrderFilter.BY_ID -> {
        users.toList().sortedBy { it.id }.toMutableList()
      }
    }
    _userListState.postValue(UserListState.Render(users.map { it.toVo() }))
  }

  fun deleteUser(id: Int) {
    users = users.toList().filter { it.id != id }.toMutableList()
    _userListState.postValue(UserListState.Render(users.map { it.toVo() }))
    viewModelScope.launch {
      repository.deteleUser(id)
    }
  }

  fun addUser(name: String, birthdayDate: String) {
    // Last usable Id
    val lastId = users.minByOrNull { it.id }?.id

    Log.d("SAVING USER TO INNOCV API", "$lastId: $name - $birthdayDate")

    viewModelScope.launch {
      repository.addUser(
        UserBO(
          lastId ?: 9999,
          name,
          birthdayDate,
        )
      )
    }
  }
}