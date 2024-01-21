package pt.isel.pdm.gomoku.services.api.users.models

data class LoginOutputModel(
    val token: String,
    val expiresIn: Long
)
