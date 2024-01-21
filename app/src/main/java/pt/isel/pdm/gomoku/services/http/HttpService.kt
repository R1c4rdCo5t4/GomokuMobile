package pt.isel.pdm.gomoku.services.http

import com.google.gson.Gson
import com.google.gson.stream.JsonReader
import okhttp3.OkHttpClient
import okhttp3.Request
import pt.isel.pdm.gomoku.services.http.media.SirenEntity
import pt.isel.pdm.gomoku.services.http.media.SirenResult
import pt.isel.pdm.gomoku.services.http.utils.APIResult.Companion.failure
import pt.isel.pdm.gomoku.services.http.utils.APIResult.Companion.success
import pt.isel.pdm.gomoku.services.http.utils.InvalidResponseException
import pt.isel.pdm.gomoku.services.http.utils.authorizationHeader
import pt.isel.pdm.gomoku.services.http.utils.fromJson
import pt.isel.pdm.gomoku.services.http.utils.getBodyOrThrow
import pt.isel.pdm.gomoku.services.http.utils.isFailure
import pt.isel.pdm.gomoku.services.http.utils.isProblem
import pt.isel.pdm.gomoku.services.http.utils.isSiren
import pt.isel.pdm.gomoku.services.http.utils.params
import pt.isel.pdm.gomoku.services.http.utils.send
import pt.isel.pdm.gomoku.services.http.utils.toJsonBody
import pt.isel.pdm.gomoku.services.http.utils.userAgentHeader

class HttpService(
    val baseUrl: String,
    val client: OkHttpClient,
    val gson: Gson,
    val updateLinks: (Map<String, String>) -> Unit
) {
    suspend inline fun <reified T> get(
        link: String,
        params: Map<String, Any?>? = null,
        token: String? = null
    ): SirenResult<T> =
        Request.Builder()
            .url(baseUrl + link.params(params))
            .authorizationHeader(token)
            .userAgentHeader()
            .build()
            .getResponseResult()

    suspend inline fun <reified T> post(
        link: String,
        body: Any? = null,
        params: Map<String, Any?>? = null,
        token: String? = null
    ): SirenResult<T> =
        Request.Builder()
            .url(baseUrl + link.params(params))
            .authorizationHeader(token)
            .userAgentHeader()
            .post(gson.toJsonBody(body))
            .build()
            .getResponseResult()

    suspend inline fun <reified T> put(
        link: String,
        body: Any? = null,
        params: Map<String, Any?>? = null,
        token: String? = null
    ): SirenResult<T> =
        Request.Builder()
            .url(baseUrl + link.params(params))
            .authorizationHeader(token)
            .userAgentHeader()
            .put(gson.toJsonBody(body))
            .build()
            .getResponseResult()

    suspend inline fun <reified T> delete(
        link: String,
        params: Map<String, Any?>? = null,
        token: String? = null
    ): SirenResult<T> =
        Request.Builder()
            .url(baseUrl + link.params(params))
            .authorizationHeader(token)
            .userAgentHeader()
            .delete()
            .build()
            .getResponseResult()

    suspend inline fun <reified T> Request.getResponseResult(): SirenResult<T> =
        send(client) { res ->
            val body = res.getBodyOrThrow()
            val json = JsonReader(body.charStream())
            when {
                res.isSuccessful && body.isSiren -> {
                    val siren = gson.fromJson<SirenEntity<T>>(json, SirenEntity.getType<T>())
                    updateLinks(siren.getSirenLinks())
                    success(siren)
                }
                res.isFailure && body.isProblem -> failure(gson.fromJson(json))
                else -> when (res.code) {
                    NOT_FOUND, BAD_GATEWAY -> throw InvalidResponseException("Could not connect to server")
                    else -> throw InvalidResponseException("Invalid server response")
                }
            }
        }

    companion object {
        const val AUTHORIZATION_HEADER = "Authorization"
        const val USER_AGENT_HEADER = "User-Agent"
        const val TOKEN_TYPE = "Bearer"
        const val NOT_FOUND = 404
        const val BAD_GATEWAY = 502
    }
}
