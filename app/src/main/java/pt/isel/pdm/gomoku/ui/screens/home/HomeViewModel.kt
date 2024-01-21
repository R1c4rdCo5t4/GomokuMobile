package pt.isel.pdm.gomoku.ui.screens.home

import android.content.Context
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pt.isel.pdm.gomoku.domain.game.GameState
import pt.isel.pdm.gomoku.domain.home.about.Home
import pt.isel.pdm.gomoku.domain.home.leaderboard.Leaderboard
import pt.isel.pdm.gomoku.domain.home.leaderboard.LeaderboardUser
import pt.isel.pdm.gomoku.domain.home.watch.LiveGame
import pt.isel.pdm.gomoku.domain.home.watch.WatchList
import pt.isel.pdm.gomoku.services.GomokuService
import pt.isel.pdm.gomoku.services.http.utils.Rels
import pt.isel.pdm.gomoku.services.http.utils.map
import pt.isel.pdm.gomoku.storage.links.Links
import pt.isel.pdm.gomoku.storage.session.Session
import pt.isel.pdm.gomoku.storage.settings.Settings
import pt.isel.pdm.gomoku.ui.GomokuViewModel
import pt.isel.pdm.gomoku.ui.utils.LoadState
import pt.isel.pdm.gomoku.ui.utils.getOrNull
import pt.isel.pdm.gomoku.ui.utils.getOrThrow
import pt.isel.pdm.gomoku.ui.utils.idle
import pt.isel.pdm.gomoku.ui.utils.loaded
import pt.isel.pdm.gomoku.ui.utils.loading
import kotlin.math.ceil

class HomeViewModel(
    service: GomokuService,
    links: Links,
    session: Session,
    settings: Settings,
    context: Context
) : GomokuViewModel(service, links, session, settings, context) {

    private val homeFlow = MutableStateFlow<LoadState<Home>>(idle())
    private val leaderboardFlow = MutableStateFlow<LoadState<Leaderboard>>(idle())
    private val liveGamesFlow = MutableStateFlow<LoadState<WatchList>>(idle())

    val home: Flow<LoadState<Home>> get() = homeFlow.asStateFlow()
    val leaderboard: Flow<LoadState<Leaderboard>> get() = leaderboardFlow.asStateFlow()
    val liveGames: Flow<LoadState<WatchList>> get() = liveGamesFlow.asStateFlow()

    fun getHome() {
        homeFlow.value = loading()
        viewModelScope.launch {
            val result = request { service.homeService.getHome(links[Rels.HOME]) }
            homeFlow.value = loaded(result)
            if (isLoggedIn) {
                request(showError = false) {
                    service.usersService.getUserHome(links[Rels.USER_HOME], session.getToken())
                }
            }
        }
    }

    fun getLeaderboard(page: Int = 1) {
        if (page == 1) {
            leaderboardFlow.value = loading()
        }
        val storedUsers = leaderboardFlow.value.getOrNull()?.users
        viewModelScope.launch {
            val result = request {
                service.usersService.getUsers(
                    link = links[Rels.USERS],
                    page = page,
                    limit = LEADERBOARD_MAX_USERS,
                    orderBy = LEADERBOARD_ORDER_BY,
                    sort = LEADERBOARD_SORT
                )
            }.map { users ->
                users
                    .map {
                        LeaderboardUser(
                            it.name,
                            it.stats,
                            it.link ?: error("User link is null")
                        )
                    }.let {
                        val totalUsers = (storedUsers ?: emptyList()) + it
                        Leaderboard(totalUsers)
                    }
            }
            leaderboardFlow.value = loaded(result)
        }
    }

    fun getLiveGames(page: Int = 1) {
        if (page == 1) {
            liveGamesFlow.value = loading()
        }
        val storedGames = liveGamesFlow.value.getOrNull()?.games
        viewModelScope.launch {
            val games = request {
                service.gamesService.getGames(
                    link = links[Rels.GAMES],
                    state = GameState.RUNNING,
                    page = page,
                    limit = WATCH_MAX_GAMES
                )
            }.map { res ->
                res.map {
                    val black = it.black ?: error("Black player is null")
                    val white = it.white ?: error("White player is null")
                    val link = it.link ?: error("Game link is null")
                    LiveGame(black, white, link)
                }.let {
                    val totalGames = (storedGames ?: emptyList()) + it
                    WatchList(totalGames)
                }
            }
            liveGamesFlow.value = loaded(games)
        }
    }

    fun loadMoreLeaderboard() {
        val totalUsers = leaderboardFlow.value.getOrThrow().users.size
        val currentPage = ceil(totalUsers.toDouble() / LEADERBOARD_MAX_USERS).toInt()
        getLeaderboard(currentPage + 1)
    }

    fun loadMoreLiveGames() {
        val totalGames = liveGamesFlow.value.getOrThrow().games.size
        val currentPage = ceil(totalGames.toDouble() / WATCH_MAX_GAMES).toInt()
        getLiveGames(currentPage + 1)
    }

    companion object {
        const val LEADERBOARD_MAX_USERS = 10
        const val LEADERBOARD_ORDER_BY = "rating"
        const val LEADERBOARD_SORT = "desc"
        const val WATCH_MAX_GAMES = 20
    }
}
