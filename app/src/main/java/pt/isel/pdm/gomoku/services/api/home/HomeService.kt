package pt.isel.pdm.gomoku.services.api.home

import pt.isel.pdm.gomoku.domain.home.about.Author
import pt.isel.pdm.gomoku.domain.home.about.Home
import pt.isel.pdm.gomoku.services.api.home.models.GetHomeOutputModel
import pt.isel.pdm.gomoku.services.http.HttpService
import pt.isel.pdm.gomoku.services.http.utils.APIResult
import pt.isel.pdm.gomoku.services.http.utils.map

class HomeService(val http: HttpService) {
    suspend fun getHome(
        link: String
    ): APIResult<Home> =
        http.get<GetHomeOutputModel>(link)
            .map { siren ->
                with(siren.getPropertiesOrThrow()) {
                    Home(
                        title,
                        version,
                        description,
                        authors.map { Author(it.name, it.email, it.github, it.number) },
                        repository
                    )
                }
            }
}
