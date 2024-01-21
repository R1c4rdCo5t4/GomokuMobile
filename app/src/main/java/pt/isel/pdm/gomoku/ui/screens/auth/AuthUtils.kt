package pt.isel.pdm.gomoku.ui.screens.auth

const val MIN_USERNAME_LEN = 3
const val MAX_USERNAME_LEN = 20
const val MIN_EMAIL_LEN = 3
const val MAX_EMAIL_LEN = 30
const val MIN_PASSWORD_LEN = 8
const val MAX_PASSWORD_LEN = 30

fun isValidUsername(username: String) =
    username.matches(Regex("^(?=.*[a-zA-Z])[a-zA-Z0-9]*\$"))

fun isValidUsernameLength(username: String) =
    username.length in MIN_USERNAME_LEN..MAX_USERNAME_LEN

fun isValidEmail(email: String) =
    email.matches(Regex("^[A-Za-z0-9+_.-]+@(.+)\$"))

fun isValidEmailLength(email: String) =
    email.length in MIN_EMAIL_LEN..MAX_EMAIL_LEN

fun isValidPassword(password: String) =
    password.matches(Regex("(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#\$%^&*()\\-__+.]).*$"))

fun isValidPasswordLength(password: String) =
    password.length in MIN_PASSWORD_LEN..MAX_PASSWORD_LEN
