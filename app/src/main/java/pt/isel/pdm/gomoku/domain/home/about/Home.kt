package pt.isel.pdm.gomoku.domain.home.about

data class Home(
    val title: String,
    val version: String,
    val description: String,
    val authors: List<Author>,
    val repository: String
)
