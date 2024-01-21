package pt.isel.pdm.gomoku.services.http.utils

import okhttp3.MediaType.Companion.toMediaType

object MediaTypes {
    val applicationJsonMediaType = "application/json".toMediaType()
    val problemMediaType = "application/problem+json".toMediaType()
    val sirenMediaType = "application/vnd.siren+json".toMediaType()
}
