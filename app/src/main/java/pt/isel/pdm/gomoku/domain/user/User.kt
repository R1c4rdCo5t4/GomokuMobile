package pt.isel.pdm.gomoku.domain.user

data class User(
    val name: String,
    val email: String,
    val stats: Stats,
    val link: String? = null
) {
    init {
        require(name.isNotBlank()) { "Name cannot be blank" }
        require(email.isNotBlank()) { "E-mail cannot be blank" }
        require(name.matches(Regex("^(?=.*[a-zA-Z])[a-zA-Z0-9]*\$"))) { "Name does not respect format" }
    }
}
