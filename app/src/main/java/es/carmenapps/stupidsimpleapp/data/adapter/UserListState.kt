package es.carmenapps.stupidsimpleapp.data.adapter

import es.carmenapps.stupidsimpleapp.data.bo.UserBO
import es.carmenapps.stupidsimpleapp.data.vo.UserVO

sealed class UserListState {

  data class Render(val users: List<UserVO>) : UserListState()

  object Error : UserListState()
  object Loading : UserListState()
}