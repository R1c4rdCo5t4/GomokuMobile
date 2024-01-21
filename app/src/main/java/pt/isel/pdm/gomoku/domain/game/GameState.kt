package pt.isel.pdm.gomoku.domain.game

enum class GameState {
    RUNNING, WHITE_WON, BLACK_WON, DRAW;

    override fun toString(): String {
        return when (this) {
            RUNNING -> "R"
            WHITE_WON -> "W"
            BLACK_WON -> "B"
            DRAW -> "D"
        }
    }

    fun getWinner(): Color? = when (this) {
        WHITE_WON -> Color.WHITE
        BLACK_WON -> Color.BLACK
        else -> null
    }

    companion object {
        fun String.toGameState() = when (this.uppercase()) {
            "R" -> RUNNING
            "W" -> WHITE_WON
            "B" -> BLACK_WON
            "D" -> DRAW
            else -> throw IllegalArgumentException("Invalid game state")
        }
    }
}
