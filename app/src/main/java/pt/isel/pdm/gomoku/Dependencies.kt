package pt.isel.pdm.gomoku

import pt.isel.pdm.gomoku.services.GomokuService
import pt.isel.pdm.gomoku.storage.links.Links
import pt.isel.pdm.gomoku.storage.session.Session
import pt.isel.pdm.gomoku.storage.settings.Settings

interface Dependencies {
    val service: GomokuService
    val links: Links
    val session: Session
    val settings: Settings
}
