package pt.isel.pdm.gomoku.services.http.media

import com.google.gson.reflect.TypeToken
import pt.isel.pdm.gomoku.services.http.utils.APIResult
import java.lang.reflect.Type

typealias SirenResult<T> = APIResult<SirenEntity<T>>

/**
 * Represents a hypermedia entity
 * @property clazz the classes of the entity (optional)
 * @property properties the properties of the entity (optional)
 * @property links the links of the entity (optional)
 * @property actions the actions of the entity (optional)
 * @property entities the sub-entities of the entity (optional)
 * @param T the type of the properties of the entity
 */
data class SirenEntity<T>(
    override val clazz: List<String>? = null,
    override val properties: T? = null,
    override val links: List<Link>? = null,
    override val actions: List<Action>? = null,
    override val entities: List<SubEntity>? = null
) : Entity<T> {

    companion object {
        inline fun <reified T> getType(): Type? = object : TypeToken<SirenEntity<T>>() {}.type
    }
}
