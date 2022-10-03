package es.carmenapps.stupidsimpleapp.data.remote

import es.carmenapps.stupidsimpleapp.data.remote.dto.UserDTO
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*
import java.util.*

interface RemoteService {

  @GET("Health")
  suspend fun getHealth(): Response<String>?

  @GET("User")
  suspend fun getAllUsers(): Response<List<UserDTO>>?

  @POST("User")
  @Headers("Content-Type: application/json")
  suspend fun addOneUser(@Body userDTO: UserDTO): Response<Unit>

  @PUT("User")
  @Headers("Content-Type: application/json")
  suspend fun updateUser(@Body userDTO: UserDTO): Response<Unit>

  @DELETE("User/{id}")
  suspend fun deleteUser(@Path("id") id: Int)

  @GET("User/{id}")
  suspend fun getOneUser(
    @Path("id") id: Int
  ): Response<UserDTO?>

}