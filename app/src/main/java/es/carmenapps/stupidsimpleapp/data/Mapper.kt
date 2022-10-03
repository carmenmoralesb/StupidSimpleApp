package es.carmenapps.stupidsimpleapp.data

import es.carmenapps.stupidsimpleapp.R
import es.carmenapps.stupidsimpleapp.data.bo.UserBO
import es.carmenapps.stupidsimpleapp.data.remote.dto.UserDTO
import es.carmenapps.stupidsimpleapp.data.vo.UserVO
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Period
import java.util.*

object Mappers {

  fun List<UserDTO>.toBo(): List<UserBO> =
    this.map { it.toBO() }

  fun UserDTO.toBO(): UserBO {
    return UserBO(
      id = this.id,
      name = this.name.orEmpty(),
      birthDay = this.birthDate
    )
  }

  fun UserBO.toVo(): UserVO =
    UserVO(
      id = this.id.toString(),
      name = this.name,
      birthDay = getBirthDayFormatted(this.birthDay),
      photo = R.drawable.ic_no_profile_picture_icon
    )

  fun UserBO.toDto(): UserDTO =
    UserDTO(
      id = this.id,
      name = this.name,
      birthDate = this.birthDay
    )

  private fun getBirthDayFormatted(birthDate: String): String {
    // example of dateformat 2022-09-08T00:35:36
    val splitDate = birthDate.split("T").getOrNull(0)?.split("-")
    return "${splitDate?.getOrNull(2)}/${splitDate?.getOrNull(1)}/${splitDate?.getOrNull(0)}"
  }
}
