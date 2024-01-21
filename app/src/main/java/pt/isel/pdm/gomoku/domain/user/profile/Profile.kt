package pt.isel.pdm.gomoku.domain.user.profile

import pt.isel.pdm.gomoku.domain.user.User

class Profile(val user: User, val games: List<UserGame>)
