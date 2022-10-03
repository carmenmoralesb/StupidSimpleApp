package es.carmenapps.stupidsimpleapp.data.adapter

import es.carmenapps.stupidsimpleapp.data.remote.dto.UserDTO
import es.carmenapps.stupidsimpleapp.data.vo.UserVO

sealed class UserDetailState {

  data class Render(val user: UserVO): UserDetailState()

  object Error: UserDetailState()
  object Loading: UserDetailState()
}