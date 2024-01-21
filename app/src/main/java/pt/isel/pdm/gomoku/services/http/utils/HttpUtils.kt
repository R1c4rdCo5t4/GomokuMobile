package pt.isel.pdm.gomoku.services.http.utils

import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.stream.JsonReader
import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.internal.EMPTY_REQUEST
import pt.isel.pdm.gomoku.services.http.HttpService.Companion.AUTHORIZATION_HEADER
import pt.isel.pdm.gomoku.services.http.HttpService.Companion.TOKEN_TYPE
import pt.isel.pdm.gomoku.services.http.HttpService.Companion.USER_AGENT_HEADER
import pt.isel.pdm.gomoku.services.http.media.SubEntity
import java.io.IOException
import java.lang.reflect.Type
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

val Response.isFailure get() = !isSuccessful
val ResponseBody.isSiren get() = contentType() == MediaTypes.sirenMediaType
val ResponseBody.isProblem get() = contentType() == MediaTypes.problemMediaType

suspend fun <T> Request.send(client: OkHttpClient, handler: (Response) -> T): T =
    suspendCancellableCoroutine { cont ->
        client.newCall(this).let { call ->
            call.enqueue(
                object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        cont.resumeWithException(e)
                    }
                    override fun onResponse(call: Call, response: Response) {
                        try {
                            cont.resume(handler(response))
                        } catch (th: Throwable) {
                            cont.resumeWithException(th)
                        }
                    }
                }
            )
            cont.invokeOnCancellation { call.cancel() }
        }
    }

fun Response.getBodyOrThrow(): ResponseBody = body
    ?: throw IllegalArgumentException("Response body is null")

inline fun <reified T> Gson.fromJson(json: JsonReader): T = fromJson(json, T::class.java)

fun Request.Builder.authorizationHeader(token: String?) =
    token?.let { header(AUTHORIZATION_HEADER, "$TOKEN_TYPE $it") } ?: this

fun Request.Builder.userAgentHeader() = header(USER_AGENT_HEADER, "Android")

fun Gson.toJsonBody(body: Any?): RequestBody = body?.let {
    toJson(body).toRequestBody(MediaTypes.applicationJsonMediaType)
} ?: EMPTY_REQUEST

class SubEntityDeserializer : JsonDeserializer<SubEntity> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext
    ): SubEntity =
        when (json?.asJsonObject?.has("href")) {
            true -> context.deserialize(json, SubEntity.EmbeddedLink::class.java)
            false -> context.deserialize(json, SubEntity.EmbeddedRepresentation::class.java)
            null -> throw IllegalArgumentException("Unknown sub-entity type")
        }
}
