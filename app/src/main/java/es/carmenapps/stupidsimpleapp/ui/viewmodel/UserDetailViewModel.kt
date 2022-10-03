package es.carmenapps.stupidsimpleapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.carmenapps.stupidsimpleapp.data.Mappers.toVo
import es.carmenapps.stupidsimpleapp.data.adapter.UserDetailState
import es.carmenapps.stupidsimpleapp.data.bo.UserBO
import es.carmenapps.stupidsimpleapp.data.repository.Repository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(
  private val repository: Repository
) : ViewModel() {

  private val _userDetailState = MutableLiveData<UserDetailState>()
  val userDetailState: LiveData<UserDetailState>
    get() = _userDetailState

  private var user: UserBO? = null

  fun init(id: String) {
    _userDetailState.postValue(UserDetailState.Loading)
    getUserSingle(id.toInt())
  }

  fun modifyUser(id: Int, name: String, birthDay: String) {

    viewModelScope.launch {
      val user = UserBO(
        id,
        name,
        birthDay
      )
      repository.updateUser(
        user
      )
    }
  }

  private fun getUserSingle(id: Int) {
    viewModelScope.launch {
      user = repository.getOneUser(id)
      if (user != null) {
        user?.let {
          _userDetailState.postValue(UserDetailState.Render(it.toVo()))
        }
      } else {
        _userDetailState.postValue(UserDetailState.Error)
      }
    }
  }
}