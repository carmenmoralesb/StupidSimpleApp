package es.carmenapps.stupidsimpleapp

import com.google.gson.GsonBuilder
import es.carmenapps.stupidsimpleapp.data.remote.dto.UserDTO
import org.junit.Assert.*
import org.junit.Test

class UserDTOTest {

  private val loader = javaClass.classLoader!!
  private val gson = GsonBuilder().create()

  @Test
  fun parse() {
    val jsonString = String(loader.getResourceAsStream("listusers.json").readBytes())
    val actual = gson.fromJson(jsonString, Array<UserDTO>::class.java).toList()

    val expected = listOf(
      UserDTO(
        name = "test0003", birthDate = "2022-09-08T00:25:14", id = 6645
      ),
      UserDTO(name = "t1", birthDate = "2022-09-08T00:32:39", id = 6650),
      UserDTO(name = "t2", birthDate = "2022-09-08T00:33:22", id = 6651),
      UserDTO(name = "t3", birthDate = "2022-09-08T00:35:36", id = 6652),
      UserDTO(name = "t9", birthDate = "2022-09-08T00:39:04", id = 6658)
    )


    assertEquals(
      expected, actual
    )
  }
}