package pt.isel.pdm.gomoku.domain.game

typealias Color = Turn

enum class Turn {
    BLACK, WHITE;

    fun other() = if (this == BLACK) WHITE else BLACK

    companion object {
        fun String.toTurn() = when (this.uppercase()) {
            "B" -> BLACK
            "W" -> WHITE
            else -> throw IllegalArgumentException("Invalid turn")
        }
    }
}
