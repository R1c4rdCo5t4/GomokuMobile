package pt.isel.pdm.gomoku.services.api.users

import pt.isel.pdm.gomoku.domain.user.User
import pt.isel.pdm.gomoku.domain.user.login.LoginModel
import pt.isel.pdm.gomoku.services.api.users.models.GetUserOutputModel
import pt.isel.pdm.gomoku.services.api.users.models.GetUsersOutputModel
import pt.isel.pdm.gomoku.services.api.users.models.LoginInputModel
import pt.isel.pdm.gomoku.services.api.users.models.LoginOutputModel
import pt.isel.pdm.gomoku.services.api.users.models.RegisterInputModel
import pt.isel.pdm.gomoku.services.http.HttpService
import pt.isel.pdm.gomoku.services.http.media.getEmbeddedRepresentations
import pt.isel.pdm.gomoku.services.http.utils.APIResult
import pt.isel.pdm.gomoku.services.http.utils.Rels
import pt.isel.pdm.gomoku.services.http.utils.map
import pt.isel.pdm.gomoku.services.http.utils.mapUnit

class UsersService(val http: HttpService) {

    suspend fun getUserHome(
        link: String,
        token: String
    ): APIResult<Unit> =
        http.get<Unit>(
            link = link,
            token = token
        ).mapUnit()

    suspend fun register(
        link: String,
        name: String,
        email: String,
        password: String
    ): APIResult<Unit> =
        http.post<Unit>(
            link = link,
            body = RegisterInputModel(name, email, password)
        ).mapUnit()

    suspend fun login(
        link: String,
        name: String?,
        email: String?,
        password: String
    ): APIResult<LoginModel> =
        http.post<LoginOutputModel>(
            link = link,
            body = LoginInputModel(name, email, password)
        ).map { siren ->
            LoginModel(
                token = siren.getPropertiesOrThrow().token,
                userHomeLink = siren.getLink(Rels.USER_HOME)
            )
        }

    suspend fun logout(
        link: String,
        token: String
    ): APIResult<Unit> =
        http.post<Unit>(
            link = link,
            token = token
        ).mapUnit()

    suspend fun getUser(
        link: String
    ): APIResult<User> =
        http.get<GetUserOutputModel>(link)
            .map { siren ->
                with(siren.getPropertiesOrThrow()) {
                    User(name, email, stats)
                }
            }

    suspend fun getUsers(
        link: String,
        page: Int? = null,
        limit: Int? = null,
        orderBy: String? = null,
        sort: String? = null
    ): APIResult<List<User>> =
        http.get<GetUsersOutputModel>(
            link = link,
            params = mapOf(
                "page" to page,
                "limit" to limit,
                "orderBy" to orderBy,
                "sort" to sort
            )
        ).map { siren ->
            siren
                .getEmbeddedRepresentations<GetUserOutputModel>(Rels.USER, Rels.ITEM)
                .map {
                    val user = it.getPropertiesOrThrow()
                    val userLink = it.getActionLink(Rels.USER)
                    User(user.name, user.email, user.stats, userLink)
                }
        }
}
