package es.carmenapps.stupidsimpleapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import es.carmenapps.stupidsimpleapp.data.Mappers.toBo
import es.carmenapps.stupidsimpleapp.data.bo.UserBO
import es.carmenapps.stupidsimpleapp.data.remote.dto.UserDTO
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class UserMapperTest {

  @get:Rule
  val instantTaskExecutorRule = InstantTaskExecutorRule()

  //region initial
  private val listUsersActual = listOf(
    UserDTO(
      name = "test0003", birthDate = "2022-09-08T00:25:14", id = 6645
    ),
    UserDTO(name = "t1", birthDate = "2022-09-08T00:32:39", id = 6650),
    UserDTO(name = "t2", birthDate = "2022-09-08T00:33:22", id = 6651),
    UserDTO(name = "t3", birthDate = "2022-09-08T00:35:36", id = 6652),
    UserDTO(name = "t9", birthDate = "2022-09-08T00:39:04", id = 6658)
  )

  //endregion initial

  //region expected
  private val matchExtensionBOExpected = listOf(
    UserBO(
      name = "test0003", birthDay = "2022-09-08T00:25:14", id = 6645
    ),
    UserBO(name = "t1", birthDay = "2022-09-08T00:32:39", id = 6650),
    UserBO(name = "t2", birthDay = "2022-09-08T00:33:22", id = 6651),
    UserBO(name = "t3", birthDay = "2022-09-08T00:35:36", id = 6652),
    UserBO(name = "t9", birthDay = "2022-09-08T00:39:04", id = 6658)
  )
  //endregion expected

  //region Test
  @Test
  fun `should map UserDTO type to UserBO success`() = runBlocking {
    val initial = listOf(listUsersActual)
    val expected = listOf(matchExtensionBOExpected)
    val actual = initial.map { it.toBo() }
    Assert.assertEquals(expected, actual)
  }
  //endregion test
}