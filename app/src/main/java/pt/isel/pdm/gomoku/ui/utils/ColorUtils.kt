package pt.isel.pdm.gomoku.ui.utils

import pt.isel.pdm.gomoku.domain.game.Turn
import pt.isel.pdm.gomoku.ui.theme.OnPrimary
import pt.isel.pdm.gomoku.ui.theme.OnPrimaryTransparent
import pt.isel.pdm.gomoku.ui.theme.Primary
import pt.isel.pdm.gomoku.ui.theme.PrimaryTransparent

fun Turn.toRgb() = when (this) {
    Turn.BLACK -> Primary
    Turn.WHITE -> OnPrimary
}

fun Turn.toTransparentRgb() = when (this) {
    Turn.BLACK -> PrimaryTransparent
    Turn.WHITE -> OnPrimaryTransparent
}
