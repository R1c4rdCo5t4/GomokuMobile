package pt.isel.pdm.gomoku.domain.game

data class Coordinate(val row: Int, val col: Int) {
    init {
        require(row > 0) { "x must be positive" }
        require(col > 0) { "y must be positive" }
    }

    override fun toString() = "$row-$col"
}
