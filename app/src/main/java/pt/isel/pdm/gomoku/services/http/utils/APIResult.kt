package pt.isel.pdm.gomoku.services.http.utils

import pt.isel.pdm.gomoku.services.http.media.Problem
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.jvm.JvmInline

@JvmInline
value class APIResult<out T>(val value: Any?) {

    val isSuccess get() = value !is Failure
    val isFailure get() = value is Failure

    fun getOrNull(): T? =
        when {
            isFailure -> null
            else -> value as T
        }

    fun getOrThrow(): T =
        when {
            isFailure -> error("Result is failure")
            else -> value as T
        }

    fun problemOrNull(): Problem? =
        when (value) {
            is Failure -> value.problem
            else -> null
        }

    fun problemOrThrow(): Problem =
        when (value) {
            is Failure -> value.problem
            else -> error("Result is not failure")
        }

    companion object {
        fun <T> success(value: T): APIResult<T> = APIResult(value)
        fun <T> failure(problem: Problem): APIResult<T> = APIResult(Failure(problem))
    }

    data class Failure(val problem: Problem)
}

@OptIn(ExperimentalContracts::class)
inline fun <R, T> APIResult<T>.map(transform: (value: T) -> R): APIResult<R> {
    contract {
        callsInPlace(transform, InvocationKind.AT_MOST_ONCE)
    }
    return when {
        isSuccess -> APIResult.success(transform(value as T))
        else -> APIResult(value)
    }
}

@OptIn(ExperimentalContracts::class)
inline fun <T> APIResult<T>.onFailure(action: (problem: Problem) -> Unit): APIResult<T> {
    contract {
        callsInPlace(action, InvocationKind.AT_MOST_ONCE)
    }
    problemOrNull()?.let { action(it) }
    return this
}

@OptIn(ExperimentalContracts::class)
inline fun <T> APIResult<T>.onSuccess(action: (value: T) -> Unit): APIResult<T> {
    contract {
        callsInPlace(action, InvocationKind.AT_MOST_ONCE)
    }
    if (isSuccess) action(value as T)
    return this
}

fun APIResult<*>.mapUnit(): APIResult<Unit> {
    return map {}
}
