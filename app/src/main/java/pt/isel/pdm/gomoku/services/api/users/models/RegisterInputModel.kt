package pt.isel.pdm.gomoku.services.api.users.models

data class RegisterInputModel(
    val name: String,
    val email: String,
    val password: String
)
