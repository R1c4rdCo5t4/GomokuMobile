package pt.isel.pdm.gomoku.services.http.media

import com.google.gson.Gson
import pt.isel.pdm.gomoku.services.http.media.Entity.Companion.gson
import pt.isel.pdm.gomoku.services.http.media.SubEntity.EmbeddedLink
import pt.isel.pdm.gomoku.services.http.media.SubEntity.EmbeddedRepresentation

/**
 * Represents a hypermedia entity
 * @property clazz the classes of the entity (optional)
 * @property properties the properties of the entity (optional)
 * @property links the links of the entity (optional)
 * @property actions the actions of the entity (optional)
 * @property entities the sub-entities of the entity (optional)
 * @param T the type of the properties of the entity
 * @see SubEntity
 * @see Action
 * @see Link
 */
interface Entity<T> {
    val clazz: List<String>?
    val properties: T?
    val entities: List<SubEntity>?
    val actions: List<Action>?
    val links: List<Link>?

    fun getPropertiesOrThrow(): T = properties ?: error("Properties field is null")

    fun getEntitiesOrThrow(): List<SubEntity> = entities ?: error("Entities field is null")

    fun getActionsOrThrow(): List<Action> = actions ?: error("Actions field is null")

    fun getLinksOrThrow(): List<Link> = links ?: error("Links field is null")

    private fun getLinks() = links?.associate { it.rel.first() to it.href.toString() } ?: emptyMap()

    private fun getActionLinks() = actions?.associate { it.name to it.href.toString() } ?: emptyMap()

    fun getLink(rel: String): String = getLinks()[rel]
        ?: throw NoSuchElementException("No link with relation '$rel'")

    fun getLinkOrNull(rel: String): String? = getLinks()[rel]

    fun getActionLink(rel: String): String = getActionLinks()[rel]
        ?: throw NoSuchElementException("No action with relation '$rel'")

    fun getActionLinkOrNull(rel: String): String? = getActionLinks()[rel]

    fun getSirenLinks() = getLinks() + getActionLinks()

    fun getEmbeddedLinks(vararg rels: String): List<EmbeddedLink> =
        entities?.filterIsInstance<EmbeddedLink>()
            ?.filter { link -> rels.all { it in link.rel } }
            ?: throw NoSuchElementException("Entity does not have any embedded links")

    companion object {
        val gson: Gson = Gson()
    }
}

inline fun <reified R> Entity<*>.getEmbeddedRepresentations(vararg rels: String) =
    entities?.filterIsInstance<EmbeddedRepresentation<R>>()
        ?.filter { link -> rels.all { it in link.rel } }
        ?.map { it.mapProperties() }
        ?: throw NoSuchElementException("Entity does not have any embedded representations")

inline fun <reified T> EmbeddedRepresentation<T>.mapProperties(): EmbeddedRepresentation<T> =
    copy(
        properties = gson.fromJson(
            gson.toJson(properties),
            T::class.java
        )
    )
