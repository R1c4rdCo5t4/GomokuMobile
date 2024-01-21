package pt.isel.pdm.gomoku.domain.user.login

data class LoginModel(
    val token: String,
    val userHomeLink: String
)
