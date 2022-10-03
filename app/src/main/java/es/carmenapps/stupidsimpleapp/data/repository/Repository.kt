package es.carmenapps.stupidsimpleapp.data.repository

import es.carmenapps.stupidsimpleapp.data.Mappers.toBO
import es.carmenapps.stupidsimpleapp.data.Mappers.toDto
import es.carmenapps.stupidsimpleapp.data.bo.UserBO
import es.carmenapps.stupidsimpleapp.data.remote.RemoteService
import javax.inject.Inject

class Repository @Inject constructor(
  private val remoteService: RemoteService
) {
  suspend fun getAllUserList(): List<UserBO> {
    val workers = mutableListOf<UserBO>()
    val resource = remoteService.getAllUsers()

    if (resource != null && resource.isSuccessful) {
      resource.body()?.map { it.toBO() }?.let {
        workers.addAll(
          it
        )
      }
    }
    return workers
  }

  suspend fun deteleUser(id: Int) {
    remoteService.deleteUser(id)
  }

  suspend fun updateUser(userBO: UserBO) {
    remoteService.updateUser(userBO.toDto())
  }

  suspend fun addUser(userBO: UserBO) {
    remoteService.addOneUser(userBO.toDto())
  }

  suspend fun getOneUser(id: Int): UserBO {
    val resource = remoteService.getOneUser(id)

    val defaultUser = UserBO(
      id = 0,
      name = "Unknown",
      ""
    )

    return if (resource.isSuccessful) {
      val user = resource.body()
      user?.toBO() ?: defaultUser
    } else {
      defaultUser
    }


  }
}