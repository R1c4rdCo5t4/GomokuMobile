package pt.isel.pdm.gomoku.storage.links

import pt.isel.pdm.gomoku.services.http.utils.Rels
import pt.isel.pdm.gomoku.storage.links.Links.Companion.HOME_LINK

class LinksMem : Links {

    private val _links = mutableMapOf<String, String>().also { it[Rels.HOME] = HOME_LINK }
    override val links get() = _links.toMap()

    override operator fun get(rel: String): String = _links[rel]
        ?: throw NoSuchElementException("No link with rel '$rel'")

    override fun getOrNull(rel: String): String? = _links[rel]

    override fun updateLinks(newLinks: Map<String, String>) {
        _links.putAll(newLinks)
    }
}
