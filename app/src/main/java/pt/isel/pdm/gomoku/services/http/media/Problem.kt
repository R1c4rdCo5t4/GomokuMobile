package pt.isel.pdm.gomoku.services.http.media

import java.net.URI

/**
 * Represents a problem that occurred in the server
 * @property type a URI that identifies the problem type
 * @property title the title the problem
 * @property detail the detail of the problem
 * @property instance a URI that identifies the location of the occurrence of the problem
 */
data class Problem(
    val type: URI? = null,
    val title: String? = null,
    val detail: String? = null,
    val instance: URI? = null
)
