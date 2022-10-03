package es.carmenapps.stupidsimpleapp.data.adapter

sealed class UserListAdapterAction {
  data class DetailAction(val id: String) : UserListAdapterAction()
  data class RemoveAction(val id: String, val name: String, val birthdate: String) : UserListAdapterAction()
  data class ModifyAction(val id: String, val name: String, val birthdate: String) : UserListAdapterAction()
}