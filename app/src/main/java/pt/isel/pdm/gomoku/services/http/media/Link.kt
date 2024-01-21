package pt.isel.pdm.gomoku.services.http.media

import java.net.URI

/**
 * Represents a link that represent a navigational transition
 * @property rel the relation of the link
 * @property href the URI of the link
 * @property title the title of the link (optional)
 * @property type the media type of the link (optional)
 */
data class Link(
    val rel: List<String>,
    val href: URI,
    val title: String? = null,
    val type: String? = null
)
