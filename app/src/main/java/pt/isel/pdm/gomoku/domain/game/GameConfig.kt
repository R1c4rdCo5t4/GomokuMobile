package pt.isel.pdm.gomoku.domain.game

data class GameConfig(val boardSize: Int, val variant: Variant, val opening: Opening) {
    init {
        require(boardSize in listOf(15, 19)) { "Board size must be either 15 or 19" }
    }
}
