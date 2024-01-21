package pt.isel.pdm.gomoku.services.api.users.models

data class LoginInputModel(
    val name: String? = null,
    val email: String? = null,
    val password: String
)
