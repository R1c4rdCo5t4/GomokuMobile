package pt.isel.pdm.gomoku.services.http.utils

fun String.addQueryParams(vararg params: Map<String, Any?>): String = params
    .flatMap { it.entries }
    .filter { it.value != null }
    .joinToString("&") { "${it.key}=${it.value}" }
    .let { if (it.isEmpty()) this else "$this?$it" }

fun String.params(params: Map<String, Any?>?): String = params?.let { addQueryParams(it) } ?: this
