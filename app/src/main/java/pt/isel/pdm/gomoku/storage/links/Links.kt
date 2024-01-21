package pt.isel.pdm.gomoku.storage.links

interface Links {
    val links: Map<String, String>

    operator fun get(rel: String): String
    fun getOrNull(rel: String): String?
    fun updateLinks(newLinks: Map<String, String>)

    companion object {
        const val HOME_LINK = "/"
    }
}
