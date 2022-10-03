package es.carmenapps.stupidsimpleapp.data.remote.dto

import com.google.gson.annotations.SerializedName
import es.carmenapps.stupidsimpleapp.data.bo.UserBO
import java.io.Serializable

data class UserContainerDTO(
  val listWorkers: List<UserDTO>
) : Serializable

data class UserDTO(
  @SerializedName("id") val id: Int,
  @SerializedName("name") val name: String?,
  @SerializedName("birthdate") val birthDate: String,
) : Serializable